package com.bk.hotel.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringJUnitWebConfig
public class TestLoggingAspect {

	@ParameterizedTest
	@MethodSource
	void testLoggingAspect(ResultActions arg) {

	}

	Stream<ResultActions> testLoggingAspect(@Autowired MockMvc mockMvc) throws Exception {
		return Stream.of(mockMvc.perform(get("/customers")),
				mockMvc.perform(get("/customers/search/byFNameLName&fName=BoJacklName=Horseman")),
				mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON).content("")),
				mockMvc.perform(put("/customers/1").contentType(MediaType.APPLICATION_JSON).content("")));
	}

}
