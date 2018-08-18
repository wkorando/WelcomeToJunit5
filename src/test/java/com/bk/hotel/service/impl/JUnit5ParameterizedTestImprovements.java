package com.bk.hotel.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

public class JUnit5ParameterizedTestImprovements {

	@ParameterizedTest
	@ValueSource(strings = { "https://google.com", "https://junit.org/" })
	public void callSomeUrls(URI uri) throws IOException {
		RestTemplate restTemplate = new RestTemplate();

		assertEquals(HttpStatus.OK,
				restTemplate.getRequestFactory().createRequest(uri, HttpMethod.GET).execute().getStatusCode());
	}

	@ParameterizedTest
	@ValueSource(strings = { "src/main/resources/testFile1.txt", "src/main/resources/testFile2.txt" })
	public void lookupSomeFiles(File file) {
		assertTrue(file.exists());
	}

	@ParameterizedTest
	@ValueSource(strings = { "en", "jp" })
	public void locales(Locale locale) {
		if (locale.getLanguage().equals("en")) {
			assertEquals("English", locale.getDisplayLanguage());
		} else {
			assertEquals("jp", locale.getDisplayLanguage());
		}
	}
	
	
}