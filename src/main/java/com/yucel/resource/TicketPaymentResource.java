package com.yucel.resource;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iyzipay.model.Payment;
import com.iyzipay.model.PaymentItem;

@Relation(value = "payment", collectionRelation = "payments")
public class TicketPaymentResource extends ResourceSupport {

	private Payment payment;

//	@JsonCreator
//	public TicketPaymentResource(@JsonProperty("binNumber") String binNumber, @JsonProperty("cardType") String cardType,
//			@JsonProperty("cardAssociation") String cardAssociation, @JsonProperty("cardFamily") String cardFamily,
//			@JsonProperty("status") String status, @JsonProperty("errorCode") String errorCode,
//			@JsonProperty("errorMessage") String errorMessage, @JsonProperty("errorGroup") String errorGroup,
//			@JsonProperty("locale") String locale, @JsonProperty("systemTime") Long systemTime,
//			@JsonProperty("conversationId") String conversationId,
//
//			@JsonProperty("price") BigDecimal price, @JsonProperty("paidPrice") BigDecimal paidPrice,
//			@JsonProperty("currency") String currency, @JsonProperty("installment") Integer installment,
//			@JsonProperty("paymentId") String paymentId, @JsonProperty("paymentStatus") String paymentStatus,
//			@JsonProperty("fraudStatus") Integer fraudStatus,
//			@JsonProperty("merchantCommissionRate") BigDecimal merchantCommissionRate,
//			@JsonProperty("merchantCommissionRateAmount") BigDecimal merchantCommissionRateAmount,
//			@JsonProperty("iyziCommissionRateAmount") BigDecimal iyziCommissionRateAmount,
//			@JsonProperty("iyziCommissionFee") BigDecimal iyziCommissionFee,
//			@JsonProperty("cardToken") String cardToken, @JsonProperty("cardUserKey") String cardUserKey,
//			@JsonProperty("basketId") String basketId, @JsonProperty("paymentItems") List<PaymentItem> paymentItems,
//			@JsonProperty("connectorName") String connectorName, @JsonProperty("authCode") String authCode,
//			@JsonProperty("phase") String phase, @JsonProperty("lastFourDigits") String lastFourDigits) {
//
//		super();
//		this.payment = new Payment();
//		this.payment.setBinNumber(binNumber);
//		this.payment.setCardType(cardType);
//		this.payment.setCardAssociation(cardAssociation);
//		this.payment.setCardFamily(cardFamily);
//		this.payment.setStatus(status);
//		this.payment.setErrorCode(errorCode);
//		this.payment.setErrorMessage(errorMessage);
//		this.payment.setErrorGroup(errorGroup);
//		this.payment.setLocale(locale);
//		this.payment.setSystemTime(systemTime);
//		this.payment.setConversationId(conversationId);
//		this.payment.setPrice(price);
//		this.payment.setPaidPrice(paidPrice);
//		this.payment.setCurrency(currency);
//		this.payment.setInstallment(installment);
//		this.payment.setPaymentId(paymentId);
//		this.payment.setPaymentStatus(paymentStatus);
//		this.payment.setFraudStatus(fraudStatus);
//		this.payment.setMerchantCommissionRate(merchantCommissionRate);
//		this.payment.setMerchantCommissionRateAmount(merchantCommissionRateAmount);
//		this.payment.setIyziCommissionRateAmount(iyziCommissionRateAmount);
//		this.payment.setIyziCommissionFee(iyziCommissionFee);
//		this.payment.setCardToken(cardToken);
//		this.payment.setCardUserKey(cardUserKey);
//		this.payment.setBasketId(basketId);
//		this.payment.setPaymentItems(paymentItems);
//		this.payment.setConnectorName(connectorName);
//		this.payment.setAuthCode(authCode);
//		this.payment.setPhase(phase);
//		this.payment.setLastFourDigits(lastFourDigits);
//		
//	}

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

	public BigDecimal getPrice() {
		return payment.getPrice();
	}

	public BigDecimal getPaidPrice() {
		return payment.getPaidPrice();
	}

	public String getCurrency() {
		return payment.getCurrency();
	}

	public Integer getInstallment() {
		return payment.getInstallment();
	}

	public String getPaymentId() {
		return payment.getPaymentId();
	}

	public String getPaymentStatus() {
		return payment.getPaymentStatus();
	}

	public Integer getFraudStatus() {
		return payment.getFraudStatus();
	}

	public BigDecimal getMerchantCommissionRate() {
		return payment.getMerchantCommissionRate();
	}

	public BigDecimal getMerchantCommissionRateAmount() {
		return payment.getMerchantCommissionRateAmount();
	}

	public BigDecimal getIyziCommissionRateAmount() {
		return payment.getIyziCommissionRateAmount();
	}

	public BigDecimal getIyziCommissionFee() {
		return payment.getIyziCommissionFee();
	}

	public String getCardToken() {
		return payment.getCardToken();
	}

	public String getCardUserKey() {
		return payment.getCardUserKey();
	}

	public String getBasketId() {
		return payment.getBasketId();
	}

	public List<PaymentItem> getPaymentItems() {
		return payment.getPaymentItems();
	}

	public String getConnectorName() {
		return payment.getConnectorName();
	}

	public String getAuthCode() {
		return payment.getAuthCode();
	}

	public String getPhase() {
		return payment.getPhase();
	}

	public String getLastFourDigits() {
		return payment.getLastFourDigits();
	}

	@JsonIgnore
	public Payment getPayment() {
		return payment;
	}

}
