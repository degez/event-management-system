package com.yucel.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.iyzipay.model.PaymentCard;
import com.yucel.model.DiscountCodes;

/**
 * @author yucel.ozan
 *
 */
public class EventManagementUtils {

	public static DiscountCodes getIfDiscountExists(String discountCode) {

		if (DiscountCodes.GAMMA.toString().equals(discountCode)) {

			return DiscountCodes.GAMMA;

		} else if (DiscountCodes.CUNNINGHAM.toString().equals(discountCode)) {

			return DiscountCodes.CUNNINGHAM;

		} else if (DiscountCodes.BECK.toString().equals(discountCode)) {

			return DiscountCodes.BECK;

		} else if (DiscountCodes.AGILE.toString().equals(discountCode)) {

			return DiscountCodes.AGILE;

		} else {
			return null;
		}

	}

	public static LocalDate parseDateWithFormat(String dateStr, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

		LocalDate localDate = LocalDate.parse(dateStr, formatter);
		
		return localDate;
	}

	public static String generateConversationId(PaymentCard paymentCard) {
		String conversationId = "";
		conversationId = paymentCard.getCardNumber().replace(" ", "").trim() + "-" + System.currentTimeMillis();
		return conversationId;
	}
}
