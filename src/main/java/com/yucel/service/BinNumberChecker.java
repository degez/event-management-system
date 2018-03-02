package com.yucel.service;

import com.iyzipay.model.BinNumber;
import com.iyzipay.model.Locale;

public interface BinNumberChecker {
	
	public BinNumber checkAndRetrieveBinNumber(Locale locale, String conversationId, String binNumber);
	
}
