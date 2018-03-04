package com.yucel.persistence.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yucel.model.CreatePaymentRequestWrapper;
import com.yucel.persistence.repository.TicketPaymentRepository;

@Service
public class TicketPaymentPersistenceService {
	
	@Resource
	TicketPaymentRepository ticketPaymentRepository;
	
	public CreatePaymentRequestWrapper findByConversationId(String conversationId) {
		return ticketPaymentRepository.findById(conversationId);
	}
	
	public CreatePaymentRequestWrapper save(CreatePaymentRequestWrapper createPaymentRequest) {
		return ticketPaymentRepository.save(createPaymentRequest);
	}
	
	public List<CreatePaymentRequestWrapper> findAll(){
		return ticketPaymentRepository.findAll();
	}
}
