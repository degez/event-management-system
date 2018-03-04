package com.yucel.prototype;

import java.util.HashMap;
import java.util.Map;

import com.yucel.prototype.model.AddressPrototype;
import com.yucel.prototype.model.BasketItemPrototype;
import com.yucel.prototype.model.BuyerPrototype;

public class PaymentRequestObjectFactory {
	private static final Map<String, PaymentRequestObject> prototypes = new HashMap<>();

	static {
		prototypes.put(BuyerPrototype.class.getName(), new BuyerPrototype());
		prototypes.put(AddressPrototype.class.getName(), new AddressPrototype());
		prototypes.put(BasketItemPrototype.class.getName(), new BasketItemPrototype());
	}

	public static PaymentRequestObject getPrototype(String type) {
		try {
			return prototypes.get(type);
		} catch (NullPointerException ex) {
			System.out.println("Prototype with name: " + type + " does not exist");
			return null;
		}
	}
}
