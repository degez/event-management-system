package com.yucel.prototype.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iyzipay.model.Buyer;
import com.yucel.prototype.PaymentRequestObject;

/**
 * Provides a clone for Buyer object with 
 * static fields for payment for demo purposes.
 * You need to set ID, name and surname yourself after the 
 * clone operation.
 * 
 * @author yucel.ozan
 *
 */
public class BuyerPrototype extends Buyer implements PaymentRequestObject {
	
	public BuyerPrototype() {

		this.setGsmNumber("+905350000000");
		this.setEmail("email@email.com");
		this.setIdentityNumber("10000000000");
		this.setLastLoginDate("2018-03-04 12:43:35");
		this.setRegistrationDate("2018-03-04 02:43:35");
		this.setRegistrationAddress("Dummy address for assignment");
		this.setIp("127.0.0.1");
		this.setCity("Istanbul");
		this.setCountry("Turkey");
		this.setZipCode("34000");
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@JsonIgnore
	public Object clone() {
		BuyerPrototype buyerPrototype = new BuyerPrototype();
		buyerPrototype.setGsmNumber(getGsmNumber());
		buyerPrototype.setEmail(getEmail());
		buyerPrototype.setIdentityNumber(getIdentityNumber());
		buyerPrototype.setLastLoginDate(getLastLoginDate());
		buyerPrototype.setRegistrationDate(getRegistrationDate());
		buyerPrototype.setRegistrationAddress(getRegistrationAddress());
		buyerPrototype.setIp(getIp());
		buyerPrototype.setCity(getCity());
		buyerPrototype.setCountry(getCountry());
		buyerPrototype.setZipCode(getZipCode());
		
		return buyerPrototype;
	}
}
