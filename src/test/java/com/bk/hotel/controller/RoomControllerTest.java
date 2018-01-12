package com.bk.hotel.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bk.hotel.model.Room;
import com.bk.hotel.service.RoomService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RoomController.class, secure = false)
public class RoomControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoomService roomService;
	
	@Test
	public void testSuccessfulCallToCustomersSearchByRoomType() throws Exception {
		when(roomService.findRoomsByType("Single")).thenReturn(Arrays.asList(new Room()));
		mockMvc.perform(get("/rooms/search?roomType=Single")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	public void testCallToCustomersSearchNoParamsPassed() throws Exception {
		mockMvc.perform(get("/rooms/search")).andExpect(status().isBadRequest());
	}

}
