package com.yucel.service;

import com.iyzipay.model.Payment;
import com.iyzipay.request.CreatePaymentRequest;
import com.yucel.model.IncomingPaymentPayload;

public interface PaymentRequestBuilder {
	
	public CreatePaymentRequest buildPaymentRequest(Payment payment, IncomingPaymentPayload paymentPayload);

}
