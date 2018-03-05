package com.yucel.prototype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yucel.prototype.model.AddressPrototype;
import com.yucel.prototype.model.BasketItemPrototype;
import com.yucel.prototype.model.BuyerPrototype;

public class PaymentRequestObjectFactory {
	private static final Map<String, PaymentRequestObject> prototypes = new HashMap<>();
	
	private static Logger logger = LoggerFactory.getLogger(PaymentRequestObjectFactory.class);


	private PaymentRequestObjectFactory() {
		// no need to instantiate this object 
	}
	
	static {
		prototypes.put(BuyerPrototype.class.getName(), new BuyerPrototype());
		prototypes.put(AddressPrototype.class.getName(), new AddressPrototype());
		prototypes.put(BasketItemPrototype.class.getName(), new BasketItemPrototype());
	}

	public static PaymentRequestObject getPrototype(String type) {
		
		PaymentRequestObject paymentRequestObject = prototypes.get(type);
		
		if(paymentRequestObject != null) {
			return paymentRequestObject;
		}else {
			logger.error("Prototype with name: " + type + " does not exist");
			return null;
		}
	}
}
