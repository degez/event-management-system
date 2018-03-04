package com.yucel.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import com.iyzipay.model.Payment;
import com.yucel.controller.TicketPaymentController;


@Service
public class TicketPaymentResourceAssembler extends ResourceAssemblerSupport<Payment, TicketPaymentResource> {
	@Autowired
	EntityLinks entityLinks;

	public TicketPaymentResourceAssembler() {
		super(TicketPaymentController.class, TicketPaymentResource.class);
	}

	@Override
	public TicketPaymentResource toResource(Payment entity) {

		TicketPaymentResource resource = createResourceWithId(entity.getPaymentId(), entity);
		return resource;
	}

	@Override
	protected TicketPaymentResource instantiateResource(Payment entity) {
		return new TicketPaymentResource(entity);
	}

	public Link linkToSingleResource(Payment entity) {
		return entityLinks.linkToSingleResource(TicketPaymentResource.class, entity.getConversationId());
	}
}
