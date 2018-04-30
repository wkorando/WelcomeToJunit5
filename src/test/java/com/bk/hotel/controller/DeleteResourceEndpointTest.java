package com.bk.hotel.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public interface DeleteResourceEndpointTest<I> extends EndpointTest {

	I getExistingResource();

	I getNonExistingResoruce();

	/**
	 * JSON array of error message(s) to be returned to the client.
	 * 
	 * @return
	 */
	String failedDeleteResourceErrorMessages();

	void mockDeleteExistingResourceBehavior();

	void mockDeleteNonExistingResourceBehavior();

	@Test
	default void testDeleteExistingResource() throws Exception {
		mockDeleteExistingResourceBehavior();
		getMockMvc().perform(delete(baseEndpoint() + "/" + getExistingResource())).andExpect(status().isNoContent());
	}

	@Test
	default void testDeleteNonExistingResource() throws Exception {
		mockDeleteNonExistingResourceBehavior();
		getMockMvc().perform(delete(baseEndpoint() + "/" + getNonExistingResoruce())).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json("{ \"errorMessages\" : [" + failedDeleteResourceErrorMessages() + "]}"));
	}
}
