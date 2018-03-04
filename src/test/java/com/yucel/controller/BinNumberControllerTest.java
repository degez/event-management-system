package com.yucel.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.yucel.EventManagementApplication;
import com.yucel.model.BinNumberResourceConstant;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = EventManagementApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class BinNumberControllerTest {

	private static final String ERROR_CODE_KEY = "errorCode";
	private static final String BIN_NUMBER = "589004";
	private static final String INVALID_BIN_NUMBER = "101001";
	private static final String BIN_NUMBER_ERROR_CODE = "5066";

	@Autowired
	private MockMvc mvc;

	@Test
	public void test_checkBinNumber() throws Exception {

		MockHttpServletRequestBuilder request = get(BinNumberResourceConstant.ROOT
				+ "/" + BIN_NUMBER).contentType(MediaType.APPLICATION_JSON);
		
		ResultActions result = mvc.perform(request);
		
		result.andExpect(status().isOk());

	}
	@Test
	public void test_checkInvalidBinNumber() throws Exception {
		
		MockHttpServletRequestBuilder request = get(BinNumberResourceConstant.ROOT
				+ "/" + INVALID_BIN_NUMBER).contentType(MediaType.APPLICATION_JSON);
		
		ResultActions result = mvc.perform(request);
		
		result.andExpect(status().isOk())
		.andExpect(jsonPath(ERROR_CODE_KEY, is(BIN_NUMBER_ERROR_CODE)));
		
	}

}
