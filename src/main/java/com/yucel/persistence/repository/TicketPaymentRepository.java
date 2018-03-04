package com.yucel.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yucel.model.CreatePaymentRequestWrapper;

public interface TicketPaymentRepository extends MongoRepository<CreatePaymentRequestWrapper, String>{
	
	public CreatePaymentRequestWrapper findById(String conversationId);
	
}
