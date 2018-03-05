package com.yucel.service;

import com.iyzipay.model.BinNumber;
import com.iyzipay.model.Locale;
import com.iyzipay.model.Payment;

public interface BinNumberChecker {
	
	/**
	 * Checks bin number using iyzico API
	 * 
	 * @param locale
	 * @param conversationId
	 * @param binNumber
	 * @return BinNumber
	 */
	public BinNumber checkAndRetrieveBinNumber(Locale locale, String conversationId, String binNumber);

	/**
	 * applies the date rules for the payment
	 * 
	 * @param binNumberObj
	 * @param payment
	 * @return Payment
	 */
	public Payment processPaymentRulesForTicketSelling(BinNumber binNumberObj, Payment payment);
	
}
