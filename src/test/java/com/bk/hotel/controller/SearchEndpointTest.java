package com.bk.hotel.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

@TestInstance(Lifecycle.PER_CLASS)
public interface SearchEndpointTest extends EndpointTest {

	Stream<SuccessfulSearchScenario> successfulSearchScenarios();
	
	@ParameterizedTest
	@MethodSource("successfulSearchScenarios")
	default void testResourcesSearch(SuccessfulSearchScenario successfulSearch) throws Exception {
		successfulSearch.mockedBehavior();
		getMockMvc().perform(get(baseEndpoint() + "/search/" + successfulSearch.getSearchQuery()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(successfulSearch.getJsonResponse()));
	}
}
