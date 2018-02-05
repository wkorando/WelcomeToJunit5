package com.bk.hotel.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.MediaType;

public interface GetResourceEndpointTest<T, I> extends EndpointTest {

	I getExistingResource();

	I getNonExistingResoruce();

	T foundResource();

	String getFoundResourceJsonContent();

	OngoingStubbing<T> mockExistingBehavior();

	OngoingStubbing<List<T>> mockFindAllResourcesBehavior();

	OngoingStubbing<T> mockNonExistingBehavior();

	@Test
	default void testExistingResource() throws Exception {
		mockExistingBehavior().thenReturn(foundResource());
		getMockMvc().perform(get(baseEndpoint() + "/" + getExistingResource())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(getFoundResourceJsonContent()));
	}

	@Test
	default void testGetAllResources() throws Exception {
		mockFindAllResourcesBehavior().thenReturn(Arrays.asList(foundResource()));
		getMockMvc().perform(get(baseEndpoint())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json("[" + getFoundResourceJsonContent() + "]"));
	}

	@Test
	default void testNonExistingResource() throws Exception {
		mockNonExistingBehavior().thenReturn(null);
		getMockMvc().perform(get(baseEndpoint() + "/" + getNonExistingResoruce())).andExpect(status().isNotFound());
	}
}
