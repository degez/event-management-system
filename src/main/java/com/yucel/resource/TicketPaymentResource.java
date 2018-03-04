package com.yucel.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iyzipay.model.Payment;

@Relation(value = "payment", collectionRelation = "payments")
public class TicketPaymentResource extends ResourceSupport {

	private Payment payment;

	@JsonCreator
	public TicketPaymentResource(@JsonProperty("binNumber") String binNumber, @JsonProperty("cardType") String cardType,
			@JsonProperty("cardAssociation") String cardAssociation, @JsonProperty("cardFamily") String cardFamily,
			 @JsonProperty("status") String status,
			@JsonProperty("errorCode") String errorCode, @JsonProperty("errorMessage") String errorMessage,
			@JsonProperty("errorGroup") String errorGroup, @JsonProperty("locale") String locale,
			@JsonProperty("systemTime") Long systemTime, @JsonProperty("conversationId") String conversationId) {
		 super();
		this.payment = new Payment();
		this.payment.setBinNumber(binNumber);
		this.payment.setCardType(cardType);
		this.payment.setCardAssociation(cardAssociation);
		this.payment.setCardFamily(cardFamily);
		this.payment.setStatus(status);
		this.payment.setErrorCode(errorCode);
		this.payment.setErrorMessage(errorMessage);
		this.payment.setErrorGroup(errorGroup);
		this.payment.setLocale(locale);
		this.payment.setSystemTime(systemTime);
		this.payment.setConversationId(conversationId);
	}

	public TicketPaymentResource(Payment payment) {
		 super();
		this.payment = payment;
	}

	public String getStatus() {
		return payment.getStatus();
	}

	public String getErrorCode() {
		return payment.getErrorCode();
	}

	public String getErrorMessage() {
		return payment.getErrorMessage();
	}

	public String getErrorGroup() {
		return payment.getErrorGroup();
	}

	public String getLocale() {
		return payment.getLocale();
	}

	public long getSystemTime() {
		return payment.getSystemTime();
	}

	public String getConversationId() {
		return payment.getConversationId();
	}

	@JsonProperty("binNumber")
	public String getBinNo() {
		return payment.getBinNumber();
	}

	public String getCardType() {
		return payment.getCardType();
	}

	public String getCardAssociation() {
		return payment.getCardAssociation();
	}

	public String getCardFamily() {
		return payment.getCardFamily();
	}

	@JsonIgnore
	public Payment getPayment() {
		return payment;
	}

}
