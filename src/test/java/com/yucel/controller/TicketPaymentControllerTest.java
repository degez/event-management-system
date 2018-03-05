package com.yucel.controller;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
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
import com.yucel.model.DiscountCodes;
import com.yucel.model.IncomingPaymentPayload;
import com.yucel.model.TicketPaymentResourceConstant;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = EventManagementApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class TicketPaymentControllerTest {

	private static final String HSBC_DEBIT_CARD_NO = "4059030000000009";
	private static final String CREDIT_CARD_NO = "5526080000000006";
	private static final String INVALID_BIN_CREDIT_CARD_NO = "1000040000000011";
	private static final String DEBIT_CARD_NO = "4475050000000003";
	private static final String DENIZBANK_CREDIT_CARD_NO = "4603450000000000";
	private static final int INVALID_QUANTITY = -1;
	private static final String ERROR_CODE_KEY = "errorCode";

	private static final String DISCOUNT_ERROR_CODE = "DISCOUNT_DATE_ERROR";
	private static final String QUANTITY_ERROR_CODE = "QUANTITY_ERROR_CODE";
	private static final String BIN_NUMBER_ERROR_CODE = "5066";
	private static final String MISSING_INFO_ERROR_CODE = "MISSING_INFO_ERROR_CODE";
	private static final String TICKET_NOT_FOUND_ERROR_CODE = "could not find payment with the id: '-1'.";
	private static final String PAYMENT_NOT_SUPPORTED_ERROR_CODE = "PAYMENT_NOT_SUPPORTED";
	private static final Object BINDING_ERROR_CODE = "Invalid IncomingPaymentPayload";

	// private static final String BIN_NUMBER = "589004";
	// private static final String INVALID_BIN_NUMBER = "101001";
	// private static final String BIN_NUMBER_ERROR_CODE = "5066";

	private IncomingPaymentPayload payload;

	@Autowired
	private MockMvc mvc;

	@Before
	public void prepare() {
		PaymentCard card = new PaymentCard();
		card.setCardHolderName("yucel ozan");
		card.setCardNumber(CREDIT_CARD_NO);
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

	@Test
	public void test_doPaymentBindingError() throws Exception {

		boolean isRightException = false;

		payload.setQuantity(null);
		MockHttpServletRequestBuilder request = preparePaymentRequest();

		ResultActions result = null;
		try {
			result = mvc.perform(request);
		} catch (Exception e) {
			if (BINDING_ERROR_CODE.equals(e.getCause().getMessage())) {
				isRightException = true;
			}
		}

		assertTrue(isRightException);
		assertTrue(result == null);

		payload.setQuantity(1);

	}

	@Test
	public void test_doPaymentWithDebit() throws Exception {
		payload.getPaymentCard().setCardNumber(DEBIT_CARD_NO);
		MockHttpServletRequestBuilder request = preparePaymentRequest();

		ResultActions result = mvc.perform(request);

		result.andExpect(status().is2xxSuccessful());
		payload.getPaymentCard().setCardNumber(CREDIT_CARD_NO);

	}

	@Test
	public void test_doPaymentWithNotOnDateDiscount() throws Exception {
		boolean isRightException = false;
		isRightException = doDiscountedRequestWithError(DiscountCodes.GAMMA.toString());

		assertTrue(isRightException);

		isRightException = false;
		isRightException = doDiscountedRequestWithError(DiscountCodes.BECK.toString());
		assertTrue(isRightException);

		isRightException = false;
		isRightException = doDiscountedRequestWithError(DiscountCodes.CUNNINGHAM.toString());
		assertTrue(isRightException);

		isRightException = false;
		isRightException = doDiscountedRequestWithError(DiscountCodes.AGILE.toString());
		assertTrue(isRightException);

		// below method does not work because we do not get error response on
		// junit
		// result.andExpect(status().is5xxServerError()).andExpect(jsonPath(MESSAGE_KEY,
		// is(DISCOUNT_ERROR_CODE)));

	}

	private boolean doDiscountedRequestWithError(String discountCode) throws JsonProcessingException {
		payload.setDiscountCode(discountCode);
		MockHttpServletRequestBuilder request = preparePaymentRequest();

		@SuppressWarnings("unused")
		ResultActions result = null;
		try {
			result = mvc.perform(request);
		} catch (Exception e) {
			if (DISCOUNT_ERROR_CODE.equals(e.getCause().getMessage())) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void test_doPaymentWithInvalidDiscount() throws Exception {

		boolean isRightException = false;

		payload.setDiscountCode("KALDIRIM");
		MockHttpServletRequestBuilder request = preparePaymentRequest();

		ResultActions result = null;
		try {
			result = mvc.perform(request);
		} catch (Exception e) {
			if (DISCOUNT_ERROR_CODE.equals(e.getCause().getMessage())) {
				isRightException = true;
			}
		}

		assertTrue(isRightException);
		assertTrue(result == null);

	}

	@Test
	public void test_doPaymentWithUnsupportedBankForCreditCard() throws Exception {

		boolean isRightException = false;

		payload.getPaymentCard().setCardNumber(DENIZBANK_CREDIT_CARD_NO);
		MockHttpServletRequestBuilder request = preparePaymentRequest();

		ResultActions result = null;
		try {
			result = mvc.perform(request);
		} catch (Exception e) {
			if (PAYMENT_NOT_SUPPORTED_ERROR_CODE.equals(e.getCause().getMessage())) {
				isRightException = true;
			}
		}

		assertTrue(isRightException);
		assertTrue(result == null);

	}

	@Test
	public void test_doPaymentWithUnsupportedBankForDebitCard() throws Exception {

		boolean isRightException = false;

		payload.getPaymentCard().setCardNumber(HSBC_DEBIT_CARD_NO);
		MockHttpServletRequestBuilder request = preparePaymentRequest();

		ResultActions result = null;
		try {
			result = mvc.perform(request);
		} catch (Exception e) {
			if (PAYMENT_NOT_SUPPORTED_ERROR_CODE.equals(e.getCause().getMessage())) {
				isRightException = true;
			}
		}

		assertTrue(isRightException);
		assertTrue(result == null);
		payload.getPaymentCard().setCardNumber(CREDIT_CARD_NO);

	}

	@Test
	public void test_doPaymentWithInvalidBinNumber() throws Exception {

		boolean isRightException = false;

		payload.getPaymentCard().setCardNumber(INVALID_BIN_CREDIT_CARD_NO);
		MockHttpServletRequestBuilder request = preparePaymentRequest();

		ResultActions result = null;
		try {
			result = mvc.perform(request);
		} catch (Exception e) {
			if (BIN_NUMBER_ERROR_CODE.equals(e.getCause().getMessage())) {
				isRightException = true;
			}
		}

		assertTrue(isRightException);
		assertTrue(result == null);
		payload.getPaymentCard().setCardNumber(CREDIT_CARD_NO);

	}

	@Test
	public void test_doPaymentWithInvalidQuantity() throws Exception {

		boolean isRightException = false;

		payload.setQuantity(INVALID_QUANTITY);
		MockHttpServletRequestBuilder request = preparePaymentRequest();

		ResultActions result = null;
		try {
			result = mvc.perform(request);
		} catch (Exception e) {
			if (QUANTITY_ERROR_CODE.equals(e.getCause().getMessage())) {
				isRightException = true;
			}
		}

		assertTrue(isRightException);
		assertTrue(result == null);

	}

	@Test
	public void test_doPaymentWithEmptyField() throws Exception {

		boolean isRightException = false;

		payload.getPaymentCard().setCardNumber(null);
		MockHttpServletRequestBuilder request = preparePaymentRequest();

		ResultActions result = null;
		try {
			result = mvc.perform(request);
		} catch (Exception e) {
			if (MISSING_INFO_ERROR_CODE.equals(e.getCause().getMessage())) {
				isRightException = true;
			}
		}

		assertTrue(isRightException);
		assertTrue(result == null);
		payload.getPaymentCard().setCardNumber(CREDIT_CARD_NO);

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

		MockHttpServletRequestBuilder requestForGet = get(TicketPaymentResourceConstant.ROOT + "/" + conversationId)
				.contentType(MediaType.APPLICATION_JSON);

		ResultActions resultForGet = mvc.perform(requestForGet);

		resultForGet.andExpect(status().isOk()).andExpect(jsonPath(ERROR_CODE_KEY, is(nullValue())));

	}
	
	@Test
	public void test_getInvalidPayment() throws Exception {
		
		boolean isRightException = false;

		String conversationId = "-1";
		
		MockHttpServletRequestBuilder requestForGet = get(TicketPaymentResourceConstant.ROOT + "/" + conversationId)
				.contentType(MediaType.APPLICATION_JSON);
		
		ResultActions resultForGet = null;
		try {
			resultForGet = mvc.perform(requestForGet);
		} catch (Exception e) {
			if (TICKET_NOT_FOUND_ERROR_CODE.equals(e.getCause().getMessage())) {
				isRightException = true;
			}
		}
		assertTrue(isRightException);
		assertTrue(resultForGet == null);
		
	}

}
