package com.yucel.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import com.iyzipay.model.BinNumber;
import com.yucel.controller.BinNumberController;


@Service
public class BinNumberResourceAssembler extends ResourceAssemblerSupport<BinNumber, BinNumberResource> {
	@Autowired
	EntityLinks entityLinks;

	public BinNumberResourceAssembler() {
		super(BinNumberController.class, BinNumberResource.class);
	}

	@Override
	public BinNumberResource toResource(BinNumber entity) {

		BinNumberResource resource = createResourceWithId(entity.getBinNumber(), entity);
		return resource;
	}

	@Override
	protected BinNumberResource instantiateResource(BinNumber entity) {
		return new BinNumberResource(entity);
	}

	public Link linkToSingleResource(BinNumber entity) {
		return entityLinks.linkToSingleResource(BinNumberResource.class, entity.getBinNumber());
	}
}
