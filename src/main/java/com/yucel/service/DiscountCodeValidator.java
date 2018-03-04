package com.yucel.service;

import com.iyzipay.model.Payment;
import com.yucel.model.IncomingPaymentPayload;

public interface DiscountCodeValidator {
	
	public Payment checkDiscountCode(IncomingPaymentPayload incomingPaymentPayload);

}
