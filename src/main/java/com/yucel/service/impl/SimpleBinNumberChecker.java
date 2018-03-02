package com.yucel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyzipay.model.BinNumber;
import com.iyzipay.model.Locale;
import com.iyzipay.request.RetrieveBinNumberRequest;
import com.yucel.service.BinNumberChecker;
import com.yucel.util.PaymentApiOptions;

@Service
public class SimpleBinNumberChecker implements BinNumberChecker{

	@Autowired
	PaymentApiOptions options;
	
	@Override
	public BinNumber checkAndRetrieveBinNumber(Locale locale, String conversationId, String binNumber) {
		RetrieveBinNumberRequest request = new RetrieveBinNumberRequest();
        request.setLocale(locale.getValue());
        request.setConversationId(conversationId);
        request.setBinNumber(binNumber);

        BinNumber binNumberResponse = BinNumber.retrieve(request, options.getOptions());
		return binNumberResponse;
	}

}
