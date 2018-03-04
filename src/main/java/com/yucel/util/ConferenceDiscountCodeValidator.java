package com.yucel.util;

import java.time.LocalDate;

import com.iyzipay.model.Payment;
import com.yucel.model.DiscountCodes;
import com.yucel.model.IncomingPaymentPayload;
import com.yucel.service.DiscountCodeValidator;

public class ConferenceDiscountCodeValidator implements DiscountCodeValidator {

	private static final String DISCOUNT_ERROR_CODE = "DISCOUNT_DATE_ERROR";
	private static final String DISCOUNT_ERROR_MESSAGE = "Discount code is not valid for today's date!";
	private static final String DATE_FORMAT = "ddMMyyyy";

	@Override
	public Payment checkDiscountCode(IncomingPaymentPayload incomingPaymentPayload) {
		Payment payment;
		String discountCode = incomingPaymentPayload.getDiscountCode();

		DiscountCodes discountEnum = EventManagementUtils.getIfDiscountExists(discountCode);

		if (discountEnum != null) {
			payment = checkDateRange(discountEnum);
			return payment;
		} else {
			return null;
		}

	}

	private Payment checkDateRange(DiscountCodes discountEnum) {

		LocalDate startDate = EventManagementUtils.parseDateWithFormat(discountEnum.getStartDate(), DATE_FORMAT);
		LocalDate endDate = EventManagementUtils.parseDateWithFormat(discountEnum.getEndDate(), DATE_FORMAT);

		LocalDate now = LocalDate.now();
		Payment payment = new Payment();

		if (!now.isBefore(startDate) && !now.isAfter(endDate)) {
			return payment;
		}else {
			payment.setErrorCode(DISCOUNT_ERROR_CODE);
			payment.setErrorMessage(DISCOUNT_ERROR_MESSAGE);
			return payment;
		}

	}

}
