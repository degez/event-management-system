package com.yucel.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.iyzipay.model.Payment;
import com.iyzipay.request.CreatePaymentRequest;

@Document
public class CreatePaymentRequestWrapper {
	
	@Id
	private String id;

	private CreatePaymentRequest createPaymentRequest;
	
	private Payment payment;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CreatePaymentRequest getCreatePaymentRequest() {
		return createPaymentRequest;
	}

	public void setCreatePaymentRequest(CreatePaymentRequest createPaymentRequest) {
		this.createPaymentRequest = createPaymentRequest;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
}
