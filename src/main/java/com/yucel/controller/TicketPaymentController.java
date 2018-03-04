package com.yucel.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iyzipay.model.Payment;
import com.yucel.exception.InvalidRequestException;
import com.yucel.model.IncomingPaymentPayload;
import com.yucel.model.TicketPaymentResourceConstant;
import com.yucel.resource.TicketPaymentResource;
import com.yucel.resource.TicketPaymentResourceAssembler;
import com.yucel.service.TicketPaymentService;

@RestController
@ExposesResourceFor(TicketPaymentResource.class)
@RequestMapping(value = TicketPaymentResourceConstant.ROOT)
public class TicketPaymentController {

	@Autowired
	TicketPaymentResourceAssembler paymentResourceAssembler;

	@Autowired
	TicketPaymentService paymentService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Resource<String>> doPayment(@RequestBody @Valid IncomingPaymentPayload paymentRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestException("Invalid " + paymentRequest.getClass().getSimpleName(), bindingResult);
		}
		Payment payment = paymentService.tryPayment(paymentRequest);
		Resource<String> resource = new Resource<String>(payment.getPaymentId());
		resource.add(paymentResourceAssembler.linkToSingleResource(payment));
		System.out.println(payment);
		return new ResponseEntity<Resource<String>>(resource, HttpStatus.CREATED);
	}

}
