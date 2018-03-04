package com.yucel.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.iyzipay.model.Address;
import com.iyzipay.model.BasketItem;
import com.iyzipay.model.Buyer;
import com.iyzipay.model.Currency;
import com.iyzipay.model.Locale;
import com.iyzipay.model.Payment;
import com.iyzipay.model.PaymentCard;
import com.iyzipay.model.PaymentChannel;
import com.iyzipay.model.PaymentGroup;
import com.iyzipay.request.CreatePaymentRequest;
import com.yucel.model.IncomingPaymentPayload;
import com.yucel.prototype.PaymentRequestObjectFactory;
import com.yucel.prototype.model.BuyerPrototype;
import com.yucel.service.PaymentRequestBuilder;

public class PaymentRequestBuilderImpl implements PaymentRequestBuilder{


	@Override
	public CreatePaymentRequest buildPaymentRequest(Payment payment, IncomingPaymentPayload paymentPayload) {
		CreatePaymentRequest request = new CreatePaymentRequest();
		PaymentCard paymentCard = paymentPayload.getPaymentCard();
		String cardNumber = paymentCard.getCardNumber();
		BigDecimal singleItemPrice = payment.getPrice().divide(new BigDecimal(paymentPayload.getQuantity()));
		
		request.setLocale(Locale.TR.getValue());
		request.setConversationId(payment.getConversationId());
		request.setPrice(payment.getPrice());
		request.setPaidPrice(payment.getPaidPrice());
		request.setCurrency(Currency.TRY.name());
		request.setInstallment(1);
		request.setBasketId(paymentCard+"-"+System.currentTimeMillis());
		request.setPaymentChannel(PaymentChannel.WEB.name());
		request.setPaymentGroup(PaymentGroup.PRODUCT.name());

		paymentCard.setRegisterCard(0);
		request.setPaymentCard(paymentCard);

		Buyer buyer = (Buyer) PaymentRequestObjectFactory.getPrototype(BuyerPrototype.class.getName()).clone();
		buyer.setId(cardNumber);
		
		String cardHolderName = paymentCard.getCardHolderName();
		String[] splittedName = cardHolderName.split(" ");
		String firstName = "";
		String lastName = "";
		
		for (int i = 0; i < splittedName.length; i++) {
			if(i==splittedName.length-1) {
				lastName = splittedName[i];
			}else {
				firstName = firstName + splittedName[i];
			}
		}
		
		buyer.setName(firstName);
		buyer.setSurname(lastName);

		request.setBuyer(buyer);

		Address shippingAddress = (Address) PaymentRequestObjectFactory.getPrototype(Address.class.getName()).clone();
		shippingAddress.setContactName(cardHolderName);
		request.setShippingAddress(shippingAddress);

		Address billingAddress = (Address) PaymentRequestObjectFactory.getPrototype(Address.class.getName()).clone();
		billingAddress.setContactName(cardHolderName);
		request.setBillingAddress(billingAddress);

		List<BasketItem> basketItems = new ArrayList<BasketItem>();
		
		for (int i = 0; i < paymentPayload.getQuantity(); i++) {
			BasketItem basketItem = (BasketItem) PaymentRequestObjectFactory.getPrototype(BasketItem.class.getName()).clone();
			basketItem.setId("ticket"+(i+1));
			basketItem.setPrice(singleItemPrice);
			basketItems.add(basketItem);
		}
		
		request.setBasketItems(basketItems);
		
		
		return request;
	}


}
