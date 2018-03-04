package com.yucel.prototype.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iyzipay.model.BasketItem;
import com.iyzipay.model.BasketItemType;
import com.yucel.prototype.PaymentRequestObject;

/**
 * Provides a clone for BasketItem object with 
 * static fields for payment for demo purposes.
 * You need to set ID and price yourself after the 
 * clone operation.
 * 
 * @author yucel.ozan
 *
 */
public class BasketItemPrototype extends BasketItem implements PaymentRequestObject{
	
	public BasketItemPrototype() {
		this.setName("Ticket");
		this.setCategory1("Conference");
		this.setItemType(BasketItemType.VIRTUAL.name());
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@JsonIgnore
	public Object clone() {
		
		BasketItem basketItem = new BasketItem();
		basketItem.setId("BI101");
		basketItem.setName("Ticket");
		basketItem.setCategory1("Collectibles");
		basketItem.setItemType(BasketItemType.VIRTUAL.name());
		return basketItem;
		
	}
}
