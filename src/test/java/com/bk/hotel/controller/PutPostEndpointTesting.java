package com.bk.hotel.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public interface PutPostEndpointTesting extends EndpointTest {

	void mockCreateResource();
	
	void mockErrorCreatingResource();

	void mockUpdateResource();

	String newResourceId();

	String updateResourceId();

	String newResourceAsJson();
	
	String newResourceWithErrorsAsJson();

	String updateResourceAsJson();

	String createNewResourceErrorMessages();

	@Test
	default void testCreateNewResource() throws Exception {
		mockCreateResource();
		getMockMvc().perform(post(baseEndpoint()).contentType(MediaType.APPLICATION_JSON_UTF8).content(newResourceAsJson()))
				.andExpect(status().isCreated()).andExpect(header().string("Location", baseEndpoint() + "/" + newResourceId()));
	}

	@Test
	default void testClientErrorCreatingNewResource() throws Exception {
		mockErrorCreatingResource();
		getMockMvc().perform(post(baseEndpoint()).contentType(MediaType.APPLICATION_JSON_UTF8).content(newResourceWithErrorsAsJson()))
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{ \"errorMessages\" : [" + createNewResourceErrorMessages() + "]}"));
	}

	@Test
	default void testUpdateResource() throws Exception {
		mockUpdateResource();
		getMockMvc().perform(put(baseEndpoint() + "/" + updateResourceId()).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(newResourceAsJson())).andExpect(status().isNoContent());
	}
}
