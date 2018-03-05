package com.yucel.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import com.iyzipay.model.Payment;
import com.yucel.model.DiscountCodes;
import com.yucel.model.IncomingPaymentPayload;
import com.yucel.service.DiscountCodeValidator;

/**
 * This object checks if the code is a valid discount code
 * 
 * @author yucel.ozan
 *
 */
@Service
public class ConferenceDiscountCodeValidator implements DiscountCodeValidator {

	private static final String TURKEY_ZONE_CODE = "Turkey";
	private static final String DISCOUNT_ERROR_CODE = "DISCOUNT_DATE_ERROR";
	private static final String DISCOUNT_ERROR_MESSAGE = "Discount code is not valid for today's date!";
	private static final String DATE_FORMAT = "ddMMyyyy";
	private static final BigDecimal BLIND_BIRD_PRICE = new BigDecimal(250);
	private static final BigDecimal EARLY_BIRD_PRICE = new BigDecimal(500);
	private static final BigDecimal REGULAR_PRICE = new BigDecimal(750);
	private static final BigDecimal LAGGARD_PRICE = new BigDecimal(1000);

	private static final LocalDate blindBirdStart = LocalDate.of(2017, Month.JUNE, 1);
	private static final LocalDate blindBirdEnd = LocalDate.of(2018, Month.JANUARY, 15);

	private static final LocalDate earlyBirdStart = LocalDate.of(2018, Month.JANUARY, 16);
	private static final LocalDate earlyBirdEnd = LocalDate.of(2018, Month.FEBRUARY, 28);

	private static final LocalDate regularStart = LocalDate.of(2018, Month.MARCH, 1);
	private static final LocalDate regularEnd = LocalDate.of(2018, Month.APRIL, 30);

	private static final LocalDate laggardStart = LocalDate.of(2018, Month.MAY, 1);
	private static final LocalDate laggardEnd = LocalDate.of(2018, Month.MAY, 27);

	/* (non-Javadoc)
	 * @see com.yucel.service.DiscountCodeValidator#checkDiscountCode(com.yucel.model.IncomingPaymentPayload)
	 */
	@Override
	public Payment checkDiscountCode(IncomingPaymentPayload incomingPaymentPayload) {
		Payment payment = new Payment();
		String discountCode = incomingPaymentPayload.getDiscountCode();

		DiscountCodes discountEnum = EventManagementUtils.getIfDiscountExists(discountCode);

		if (discountEnum != null) {
			payment = checkDateRange(discountEnum);
			return payment;
		} else {
			payment.setErrorCode(DISCOUNT_ERROR_CODE);
			payment.setErrorMessage(DISCOUNT_ERROR_MESSAGE);
			return payment;
		}

	}

	/**
	 * We keep payment dates on the ENUM objects
	 * 
	 * @param discountEnum
	 * @return payment
	 */
	private Payment checkDateRange(DiscountCodes discountEnum) {

		LocalDate startDate = EventManagementUtils.parseDateWithFormat(discountEnum.getStartDate(), DATE_FORMAT);
		LocalDate endDate = EventManagementUtils.parseDateWithFormat(discountEnum.getEndDate(), DATE_FORMAT);

		LocalDate now = LocalDate.now(ZoneId.of(TURKEY_ZONE_CODE));
		Payment payment = new Payment();

		if (!now.isBefore(startDate) && !now.isAfter(endDate)) {
			return payment;
		} else {
			payment.setErrorCode(DISCOUNT_ERROR_CODE);
			payment.setErrorMessage(DISCOUNT_ERROR_MESSAGE);
			return payment;
		}

	}
	
	@Override
	public BigDecimal getPriceForThePeriod() {
		BigDecimal result = null;
		LocalDate now = LocalDate.now(ZoneId.of(TURKEY_ZONE_CODE));
		
		if (!now.isBefore(blindBirdStart) && !now.isAfter(blindBirdEnd)) {
			result = BLIND_BIRD_PRICE;
		} else if (!now.isBefore(earlyBirdStart) && !now.isAfter(earlyBirdEnd)) {
			result = EARLY_BIRD_PRICE;
		} else if (!now.isBefore(regularStart) && !now.isAfter(regularEnd)) {
			result = REGULAR_PRICE;
		} else if (!now.isBefore(laggardStart) && !now.isAfter(laggardEnd)) {
			result = LAGGARD_PRICE;
		}else {
			// value will stay null
		}
		
		
		return result;
	}

}
