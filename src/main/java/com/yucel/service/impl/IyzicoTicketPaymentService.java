package com.yucel.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyzipay.model.BinNumber;
import com.iyzipay.model.Locale;
import com.iyzipay.model.Payment;
import com.iyzipay.model.PaymentCard;
import com.iyzipay.request.CreatePaymentRequest;
import com.yucel.model.CreatePaymentRequestWrapper;
import com.yucel.model.DiscountCodes;
import com.yucel.model.IncomingPaymentPayload;
import com.yucel.persistence.service.TicketPaymentPersistenceService;
import com.yucel.service.BinNumberChecker;
import com.yucel.service.DiscountCodeValidator;
import com.yucel.service.PaymentApiOptions;
import com.yucel.service.PaymentRequestBuilder;
import com.yucel.service.TicketPaymentService;
import com.yucel.util.EventManagementUtils;
import com.yucel.util.TicketPaymentPreValidator;

@Service
public class IyzicoTicketPaymentService implements TicketPaymentService {

	private static final String PERIOD_ERROR_CODE = "PERIOD_ERROR";

	private static final String PERIOD_ERROR_MESSAGE = "Tickets not available for current date";

	private static Logger logger = LoggerFactory.getLogger(IyzicoTicketPaymentService.class);

	@Autowired
	PaymentApiOptions options;

	@Autowired
	DiscountCodeValidator discountCodeValidator;

	@Autowired
	BinNumberChecker binNumberChecker;

	@Autowired
	PaymentRequestBuilder paymentRequestBuilder;

	@Autowired
	TicketPaymentPersistenceService ticketPaymentPersistenceService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yucel.service.TicketPaymentService#tryPayment(com.yucel.model.
	 * IncomingPaymentPayload)
	 */
	@Override
	public Payment tryPayment(IncomingPaymentPayload paymentPayload) {
		Payment payment = new Payment();

		payment = TicketPaymentPreValidator.validateFields(paymentPayload, payment);

		String discountCode = paymentPayload.getDiscountCode();

		if (payment.getErrorCode() != null) {
			logger.error("validation of fields failed" + payment.getErrorCode());
			return payment;
		}
		logger.info("Payment Step 1 - validation of fields passed");

		PaymentCard paymentCard = paymentPayload.getPaymentCard();
		String conversationId = EventManagementUtils.generateConversationId(paymentCard);
		// After field validation we need to validate if the discount code is valid
		// if it is not, we do not need to make requests for payment
		if (discountCode != null && !"".equals(discountCode)) {
			payment = discountCodeValidator.checkDiscountCode(paymentPayload);
		}

		payment.setConversationId(conversationId);

		if (payment.getErrorCode() != null) {
			logger.error("discount is not valid" + payment.getErrorCode());
			return payment;
		}
		logger.info("Payment Step 2 - discount ops passed");

		paymentCard.setCardNumber(paymentCard.getCardNumber().replace(" ", "").trim());

		String binNumber = paymentCard.getCardNumber().substring(0, 6);

		payment = applyBinNumberConstraints(payment, conversationId, binNumber);

		if (payment.getErrorCode() != null) {
			logger.error("bin number is out of our service" + payment.getErrorCode());
			return payment;
		}
		logger.info("Payment Step 3 - bin number validation passed");

		payment = calculatePayment(payment, paymentPayload);

		if (payment.getErrorCode() != null) {
			logger.error("payment calculation failed" + payment.getErrorCode());
			return payment;
		}
		logger.info("Payment Step 4 - payment calculations passed");

		CreatePaymentRequest paymentRequest = paymentRequestBuilder.buildPaymentRequest(payment, paymentPayload);
		CreatePaymentRequestWrapper requestWrapper = new CreatePaymentRequestWrapper();
		requestWrapper.setId(conversationId);
		requestWrapper.setCreatePaymentRequest(paymentRequest);
		requestWrapper.setPayment(payment);
		// save request data before the call
		requestWrapper = ticketPaymentPersistenceService.save(requestWrapper);
		logger.info("before the request of the payment:");
		logger.info(payment.toString());

		payment = Payment.create(paymentRequest, options.getOptions());
		logger.info("Payment Step 5 - payment request made to iyzico");

		// save the result
		requestWrapper.setPayment(payment);
		requestWrapper = ticketPaymentPersistenceService.save(requestWrapper);
		logger.info("save success of the payment:");
		logger.info(payment.toString());

		return payment;
	}

	private Payment calculatePayment(Payment payment, IncomingPaymentPayload paymentPayload) {

		BigDecimal perPrice = discountCodeValidator.getPriceForThePeriod();
		BigDecimal price = BigDecimal.ZERO;
		BigDecimal discountedPrice = null;
		if (price == null) {
			payment.setErrorCode(PERIOD_ERROR_CODE);
			payment.setErrorMessage(PERIOD_ERROR_MESSAGE);
			return payment;
		}

		for (int i = 0; i < paymentPayload.getQuantity(); i++) {
			price = price.add(perPrice);
		}

		// calculate discount if there is any
		String discountCodeStr = paymentPayload.getDiscountCode();

		if (discountCodeStr != null) {
			DiscountCodes discountEnum = EventManagementUtils.getIfDiscountExists(discountCodeStr);

			BigDecimal discountPercentage = BigDecimal.valueOf(discountEnum.getDiscount());
			discountedPrice = price.subtract(EventManagementUtils.percentage(price, discountPercentage));

		}

		payment.setPrice(price);

		// we set paid price discounted if there is a discount
		if (discountedPrice != null) {
			payment.setPaidPrice(discountedPrice);
		} else {
			payment.setPaidPrice(price);
		}

		return payment;
	}

	private Payment applyBinNumberConstraints(Payment payment, String conversationId, String binNumber) {
		BinNumber binNumberObj = binNumberChecker.checkAndRetrieveBinNumber(Locale.TR, conversationId, binNumber);
		String errorCode = binNumberObj.getErrorCode();
		String errorMessage = binNumberObj.getErrorMessage();
		String errorGroup = binNumberObj.getErrorGroup();

		if (errorCode == null && errorMessage == null && errorGroup == null) {
			payment = binNumberChecker.processPaymentRulesForTicketSelling(binNumberObj, payment);
		} else {
			payment.setErrorCode(errorCode);
			payment.setErrorMessage(errorMessage);
			payment.setErrorGroup(errorGroup);
		}

		return payment;
	}

	@Override
	public Payment getPayment(String id) {

		CreatePaymentRequestWrapper createPaymentRequestWrapper = ticketPaymentPersistenceService
				.findByConversationId(id);
		if (createPaymentRequestWrapper != null) {
			return createPaymentRequestWrapper.getPayment();
		} else {
			return null;
		}
	}

}
