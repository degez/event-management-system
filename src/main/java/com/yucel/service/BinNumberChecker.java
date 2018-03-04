package com.yucel.service;

import com.iyzipay.model.BinNumber;
import com.iyzipay.model.Locale;
import com.iyzipay.model.Payment;

public interface BinNumberChecker {
	
	public BinNumber checkAndRetrieveBinNumber(Locale locale, String conversationId, String binNumber);

	public Payment processPaymentRulesForTicketSelling(BinNumber binNumberObj, Payment payment);
	
}
