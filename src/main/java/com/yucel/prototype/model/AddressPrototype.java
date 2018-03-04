package com.yucel.prototype.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iyzipay.model.Address;
import com.yucel.prototype.PaymentRequestObject;

/**
 * Provides a clone for Buyer object with 
 * static fields for payment for demo purposes.
 * You need to name yourself after the clone operation.
 * 
 * @author yucel.ozan
 *
 */
public class AddressPrototype extends Address implements PaymentRequestObject{

	public AddressPrototype() {
		this.setCity("Istanbul");
		this.setCountry("Turkey");
		this.setAddress("Dummy address for assignment");
		this.setZipCode("34000");
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@JsonIgnore
	public Object clone() {
		Address address = new Address();
		address.setContactName(getContactName());
		address.setCity(getCity());
		address.setCountry(getCountry());
		address.setAddress(getAddress());
		address.setZipCode(getZipCode());
		
		return address;
	}
}
