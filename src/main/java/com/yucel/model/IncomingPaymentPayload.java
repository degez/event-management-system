package com.yucel.model;

import javax.validation.constraints.NotNull;

import com.iyzipay.model.PaymentCard;

public class IncomingPaymentPayload {

	@NotNull
	private PaymentCard paymentCard;
	@NotNull
	private Integer quantity;
	private String discountCode;

	public PaymentCard getPaymentCard() {
		return paymentCard;
	}

	public void setPaymentCard(PaymentCard paymentCard) {
		this.paymentCard = paymentCard;
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

}
