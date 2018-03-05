package com.yucel.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iyzipay.model.BinNumber;
import com.iyzipay.model.Locale;
import com.iyzipay.model.Payment;
import com.iyzipay.request.RetrieveBinNumberRequest;
import com.yucel.model.CardTypes;
import com.yucel.service.BinNumberChecker;
import com.yucel.service.PaymentApiOptions;

@Service
public class SimpleBinNumberChecker implements BinNumberChecker {

	private static final String PAYMENT_NOT_SUPPORTED_ERROR_CODE = "PAYMENT_NOT_SUPPORTED";
	private static final String PAYMENT_NOT_SUPPORTED_ERROR_MESSAGE = "Payment not supported for this type of card or bank";
	private static Map<Long, String> validCreditCardPaymentBanksIM;
	private static Map<Long, String> validDebitCardPaymentBanksIM;

	@Autowired
	PaymentApiOptions options;

	public SimpleBinNumberChecker() {

		HashMap<Long, String> validCreditCardPaymentBanks = new HashMap<>();
		validCreditCardPaymentBanks.put(46L, "Akbank");
		validCreditCardPaymentBanks.put(62L, "Garanti Bankası");
		validCreditCardPaymentBanks.put(64L, "İş Bankası");
		validCreditCardPaymentBanks.put(111L, "Finansbank");

		validCreditCardPaymentBanksIM = Collections.unmodifiableMap(validCreditCardPaymentBanks);

		HashMap<Long, String> validDebitCardPaymentBanks = new HashMap<>();
		validDebitCardPaymentBanks.put(12L, "Halk Bankası");

		validDebitCardPaymentBanksIM = Collections.unmodifiableMap(validDebitCardPaymentBanks);

	}

	@Override
	public BinNumber checkAndRetrieveBinNumber(Locale locale, String conversationId, String binNumber) {
		RetrieveBinNumberRequest request = new RetrieveBinNumberRequest();
		request.setLocale(locale.getValue());
		request.setConversationId(conversationId);
		request.setBinNumber(binNumber);

		BinNumber binNumberResponse = BinNumber.retrieve(request, options.getOptions());
		return binNumberResponse;
	}

	@Override
	public Payment processPaymentRulesForTicketSelling(BinNumber binNumberObj, Payment payment) {

		if (CardTypes.CREDIT_CARD.toString().equals(binNumberObj.getCardType())) {

			if (validCreditCardPaymentBanksIM.containsKey(binNumberObj.getBankCode())) {
				// we allow payment for this type of card and for this bank
				payment.setCardType(CardTypes.CREDIT_CARD.toString());
			} else {
				// not allowed, we set error codes
				payment.setErrorCode(PAYMENT_NOT_SUPPORTED_ERROR_CODE);
				payment.setErrorMessage(PAYMENT_NOT_SUPPORTED_ERROR_MESSAGE);
			}

		} else if (CardTypes.DEBIT_CARD.toString().equals(binNumberObj.getCardType())) {

			if (validDebitCardPaymentBanksIM.containsKey(binNumberObj.getBankCode())) {
				// we allow payment for this type of card and for this bank
				payment.setCardType(CardTypes.DEBIT_CARD.toString());
			} else {
				// not allowed, we set error codes
				payment.setErrorCode(PAYMENT_NOT_SUPPORTED_ERROR_CODE);
				payment.setErrorMessage(PAYMENT_NOT_SUPPORTED_ERROR_MESSAGE);
			}

		}

		return payment;
	}

}
