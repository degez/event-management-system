package com.yucel.controller;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iyzipay.model.PaymentCard;
import com.yucel.EventManagementApplication;
import com.yucel.model.IncomingPaymentPayload;
import com.yucel.model.TicketPaymentResourceConstant;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = EventManagementApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class TicketPaymentControllerTest {

	private static final String ERROR_CODE_KEY = "errorCode";
//	private static final String BIN_NUMBER = "589004";
//	private static final String INVALID_BIN_NUMBER = "101001";
//	private static final String BIN_NUMBER_ERROR_CODE = "5066";

	private IncomingPaymentPayload payload;
	
	@Autowired
	private MockMvc mvc;

	@Before
	public void prepare() {
		PaymentCard card = new PaymentCard();
		card.setCardHolderName("yucel ozan");
		card.setCardNumber("5890040000000016");
		card.setExpireMonth("12");
		card.setExpireYear("2030");
		card.setRegisterCard(0);
		card.setCvc("123");

		payload = new IncomingPaymentPayload();
		payload.setPaymentCard(card);
		payload.setQuantity(1);
		payload.setDiscountCode(null);

		
	}
	
	
	@Test
	public void test_doPayment() throws Exception {

		MockHttpServletRequestBuilder request = preparePaymentRequest();

		ResultActions result = mvc.perform(request);

		result.andExpect(status().is2xxSuccessful());

	}


	private MockHttpServletRequestBuilder preparePaymentRequest() throws JsonProcessingException {
		String content;
		ObjectMapper mapper = new ObjectMapper();
	

		content = mapper.writeValueAsString(payload);
		MockHttpServletRequestBuilder request = post(TicketPaymentResourceConstant.ROOT)
				.contentType(MediaType.APPLICATION_JSON).content(content);
		return request;
	}

	@Test
	public void test_getPayment() throws Exception {
		
		MockHttpServletRequestBuilder request = preparePaymentRequest();

		ResultActions result = mvc.perform(request);
		
		String contentAsString = result.andReturn().getResponse().getContentAsString();
		JsonParser parser = new JsonParser();
		JsonObject contentJson = parser.parse(contentAsString).getAsJsonObject();
		
		String conversationId = contentJson.get("content").getAsString();
		
		
		MockHttpServletRequestBuilder requestForGet = get(TicketPaymentResourceConstant.ROOT + "/" + conversationId )
				.contentType(MediaType.APPLICATION_JSON);

		ResultActions resultForGet = mvc.perform(requestForGet);

		resultForGet.andExpect(status().isOk()).andExpect(jsonPath(ERROR_CODE_KEY, is(nullValue())));

	}

}
