package com.bk.hotel.controller;

public abstract class SuccessfulSearchScenario {

	private String searchQuery;
	private String jsonResponse;

	public SuccessfulSearchScenario(String searchQuery, String jsonResponse) {
		this.searchQuery = searchQuery;
		this.jsonResponse = jsonResponse;
	}

	public abstract void mockedBehavior();

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}

}
