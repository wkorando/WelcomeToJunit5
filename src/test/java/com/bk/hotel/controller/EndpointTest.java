package com.bk.hotel.controller;

import org.springframework.test.web.servlet.MockMvc;

public interface EndpointTest {
	String baseEndpoint();
	
	MockMvc getMockMvc();
}
