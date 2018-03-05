package com.yucel.util;

import com.iyzipay.model.Payment;
import com.iyzipay.model.PaymentCard;
import com.yucel.model.IncomingPaymentPayload;

/**
 * Makes basic validations
 * 
 * @author yucel.ozan
 *
 */
public class TicketPaymentPreValidator {

	private static final String QUANTITY_ERROR_CODE = "QUANTITY_ERROR_CODE";

	private static final String QUANTITY_ERROR_MESSAGE = "At least one ticket should be in transaction";
	
	private static final String MISSING_INFO_ERROR_CODE = "MISSING_INFO_ERROR_CODE";
	
	private static final String MISSING_INFO_MESSAGE = "Missing payment information";

	private static final int CARD_NUMBER_LENGHT = 16;

	private TicketPaymentPreValidator() {
		// no need to instantiate this object
	}
	
	public static Payment validateFields(IncomingPaymentPayload paymentPayload, Payment payment) {
		// Quantity check
		if (paymentPayload.getQuantity() < 1) {
			payment.setErrorCode(QUANTITY_ERROR_CODE);
			payment.setErrorMessage(QUANTITY_ERROR_MESSAGE);
			return payment;
		}
		
		if(!validatePaymentPayload(paymentPayload)) {
			payment.setErrorCode(MISSING_INFO_ERROR_CODE);
			payment.setErrorMessage(MISSING_INFO_MESSAGE);
			return payment;
		}
		
		return payment;
	}

	public static Boolean validatePaymentPayload(IncomingPaymentPayload paymentPayload) {
		Boolean result = Boolean.TRUE;
		PaymentCard paymentCard = null;
		if (paymentPayload != null) {
			paymentCard = paymentPayload.getPaymentCard();
		}else {
			result = Boolean.FALSE;
		}
		if (paymentCard == null || paymentCard.getCardNumber() == null
				|| paymentCard.getCardHolderName() == null || paymentCard.getExpireYear() == null
				|| paymentCard.getExpireMonth() == null || paymentCard.getCvc() == null
				|| paymentCard.getCardNumber().trim().length() != CARD_NUMBER_LENGHT) {
			result = Boolean.FALSE;
		}

		return result;
	}

}
