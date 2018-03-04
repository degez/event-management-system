package com.yucel.service;

import java.math.BigDecimal;

import com.iyzipay.model.Payment;
import com.yucel.model.IncomingPaymentPayload;

public interface DiscountCodeValidator {
	
	public Payment checkDiscountCode(IncomingPaymentPayload incomingPaymentPayload);
	
	public BigDecimal getPriceForThePeriod();
}
