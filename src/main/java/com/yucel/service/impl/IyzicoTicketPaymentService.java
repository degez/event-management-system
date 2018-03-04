package com.yucel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyzipay.model.BinNumber;
import com.iyzipay.model.Locale;
import com.iyzipay.model.Payment;
import com.iyzipay.model.PaymentCard;
import com.yucel.model.IncomingPaymentPayload;
import com.yucel.service.BinNumberChecker;
import com.yucel.service.DiscountCodeValidator;
import com.yucel.service.TicketPaymentService;
import com.yucel.util.EventManagementUtils;
import com.yucel.util.TicketPaymentPreValidator;

@Service
public class IyzicoTicketPaymentService implements TicketPaymentService {

	@Autowired
	DiscountCodeValidator discountCodeValidator;

	@Autowired
	BinNumberChecker binNumberChecker;

	@Override
	public Payment tryPayment(IncomingPaymentPayload paymentPayload) {
		Payment payment = new Payment();

		payment = TicketPaymentPreValidator.validateFields(paymentPayload, payment);

		String discountCode = paymentPayload.getDiscountCode();

		if (payment.getErrorCode() != null) {
			return payment;
		}

		// After field validation we need to validate if the discount code is valid
		// if it is not, we do not need to make requests for payment
		if (discountCode != null && !"".equals(discountCode)) {
			payment = discountCodeValidator.checkDiscountCode(paymentPayload);
		}

		if (payment.getErrorCode() != null) {
			return payment;
		}
		
		PaymentCard paymentCard = paymentPayload.getPaymentCard();
		
		String conversationId = EventManagementUtils.generateConversationId(paymentCard);
		payment.setConversationId(conversationId);
		
		paymentCard.setCardNumber(paymentCard.getCardNumber().replace(" ", "").trim());
		
		String binNumber = paymentCard.getCardNumber().substring(0, 6);
		
		payment = applyBinNumberConstraints(payment, conversationId, binNumber);
		
		if (payment.getErrorCode() != null) {
			return payment;
		}
		
		payment = calculatePayment(payment, paymentPayload);
		
		
		
		
		

		return payment;
	}

	private Payment calculatePayment(Payment payment, IncomingPaymentPayload paymentPayload) {

		
		
		return payment;
	}

	private Payment applyBinNumberConstraints(Payment payment, String conversationId, String binNumber) {
		BinNumber binNumberObj = binNumberChecker.checkAndRetrieveBinNumber(Locale.TR, conversationId, binNumber);
		String errorCode = binNumberObj.getErrorCode();
		String errorMessage = binNumberObj.getErrorMessage();
		String errorGroup = binNumberObj.getErrorGroup();
		
		if(errorCode == null && errorMessage == null && errorGroup == null) {
			payment = binNumberChecker.processPaymentRulesForTicketSelling(binNumberObj, payment);
		}else {
			payment.setErrorCode(errorCode);
			payment.setErrorMessage(errorMessage);
			payment.setErrorGroup(errorGroup);
		}
		
		return payment;
	}

}
