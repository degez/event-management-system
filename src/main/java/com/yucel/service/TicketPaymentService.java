package com.yucel.service;

import com.iyzipay.model.Payment;
import com.yucel.model.IncomingPaymentPayload;

public interface TicketPaymentService {
	
	public Payment tryPayment(IncomingPaymentPayload paymentRequest);

}
