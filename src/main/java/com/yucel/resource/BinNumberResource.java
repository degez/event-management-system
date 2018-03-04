package com.yucel.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iyzipay.model.BinNumber;

@Relation(value = "binNumber", collectionRelation = "binNumbers")
public class BinNumberResource extends ResourceSupport {

	private BinNumber binNumber;

	@JsonCreator
	public BinNumberResource(@JsonProperty("binNumber") String binNumber, @JsonProperty("cardType") String cardType,
			@JsonProperty("cardAssociation") String cardAssociation, @JsonProperty("cardFamily") String cardFamily,
			@JsonProperty("bankName") String bankName, @JsonProperty("bankCode") Long bankCode,
			@JsonProperty("commercial") Integer commercial, @JsonProperty("status") String status,
			@JsonProperty("errorCode") String errorCode, @JsonProperty("errorMessage") String errorMessage,
			@JsonProperty("errorGroup") String errorGroup, @JsonProperty("locale") String locale,
			@JsonProperty("systemTime") Long systemTime, @JsonProperty("conversationId") String conversationId) {
		 super();
		this.binNumber = new BinNumber();
		this.binNumber.setBinNumber(binNumber);
		this.binNumber.setCardType(cardType);
		this.binNumber.setCardAssociation(cardAssociation);
		this.binNumber.setCardFamily(cardFamily);
		this.binNumber.setBankName(bankName);
		this.binNumber.setBankCode(bankCode);
		this.binNumber.setCommercial(commercial);
		this.binNumber.setStatus(status);
		this.binNumber.setErrorCode(errorCode);
		this.binNumber.setErrorMessage(errorMessage);
		this.binNumber.setErrorGroup(errorGroup);
		this.binNumber.setLocale(locale);
		this.binNumber.setSystemTime(systemTime);
		this.binNumber.setConversationId(conversationId);
	}

	public BinNumberResource(BinNumber binNumber) {
		 super();
		this.binNumber = binNumber;
	}

	public String getStatus() {
		return binNumber.getStatus();
	}

	public String getErrorCode() {
		return binNumber.getErrorCode();
	}

	public String getErrorMessage() {
		return binNumber.getErrorMessage();
	}

	public String getErrorGroup() {
		return binNumber.getErrorGroup();
	}

	public String getLocale() {
		return binNumber.getLocale();
	}

	public long getSystemTime() {
		return binNumber.getSystemTime();
	}

	public String getConversationId() {
		return binNumber.getConversationId();
	}

	@JsonProperty("binNumber")
	public String getBinNo() {
		return binNumber.getBinNumber();
	}

	public String getCardType() {
		return binNumber.getCardType();
	}

	public String getCardAssociation() {
		return binNumber.getCardAssociation();
	}

	public String getCardFamily() {
		return binNumber.getCardFamily();
	}

	public String getBankName() {
		return binNumber.getBankName();
	}

	public Long getBankCode() {
		return binNumber.getBankCode();
	}

	public Integer getCommercial() {
		return binNumber.getCommercial();
	}

	@JsonIgnore
	public BinNumber getBinNumber() {
		return binNumber;
	}

}
