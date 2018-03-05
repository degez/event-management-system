package com.yucel.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iyzipay.model.Payment;
import com.yucel.exception.TicketPaymentNotFoundException;
import com.yucel.exception.ValidationOfValuesException;
import com.yucel.exception.InvalidRequestException;
import com.yucel.model.IncomingPaymentPayload;
import com.yucel.model.TicketPaymentResourceConstant;
import com.yucel.resource.TicketPaymentResource;
import com.yucel.resource.TicketPaymentResourceAssembler;
import com.yucel.service.TicketPaymentService;

@RestController
@CrossOrigin(origins = "*")
@ExposesResourceFor(TicketPaymentResource.class)
@RequestMapping(value = TicketPaymentResourceConstant.ROOT)
public class TicketPaymentController {

	private static Logger logger = LoggerFactory.getLogger(TicketPaymentController.class);

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
		String errorCode = payment.getErrorCode();
		if (errorCode != null) {
			throw new ValidationOfValuesException(errorCode);
		}

		Resource<String> resource = new Resource<String>(payment.getConversationId());
		resource.add(paymentResourceAssembler.linkToSingleResource(payment));
		logger.info("Payment: " + payment);
		return new ResponseEntity<Resource<String>>(resource, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = TicketPaymentResourceConstant.ID, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<TicketPaymentResource> getPayment(@PathVariable(value = "id") String id) {
		Payment entity = paymentService.getPayment(id);
		if (entity == null) {
			throw new TicketPaymentNotFoundException(id);
		}
		final TicketPaymentResource resource = paymentResourceAssembler.toResource(entity);
		return ResponseEntity.ok(resource);
	}

}
