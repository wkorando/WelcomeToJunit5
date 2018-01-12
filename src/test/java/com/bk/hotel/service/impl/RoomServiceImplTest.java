package com.bk.hotel.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.bk.hotel.RoomServiceException;
import com.bk.hotel.model.Room;
import com.bk.hotel.repo.RoomRepo;

public class RoomServiceImplTest {
	private List<String> roomTypes = Arrays.asList("Single", "Double", "Suite");

	@Test
	public void testFindByValidRoomType() {
		RoomRepo repo = mock(RoomRepo.class);
		RoomServiceImpl service = new RoomServiceImpl(repo, roomTypes);
		when(repo.findRoomsByRoomType("Single")).thenReturn(Arrays.asList(//
				new Room(1L, "100", "Single", new BigDecimal(145.99))));
		List<Room> rooms = service.findRoomsByType("Single");

		assertThat(rooms).extracting("id", "roomNumber", "roomType", "roomRate")
				.containsExactly(tuple(1L, "100", "Single", new BigDecimal(145.99)));
	}

	@Test(expected = RoomServiceException.class)
	public void testFindByInvalidRoomType() {
		RoomRepo repo = mock(RoomRepo.class);
		RoomServiceImpl service = new RoomServiceImpl(repo, roomTypes);
		verify(repo, times(0)).findRoomsByRoomType(any());
		try {
			service.findRoomsByType("NOT FOUND");
		} catch (RoomServiceException e) {
			assertThat(e.getMessage()).isEqualTo("Room type: NOT FOUND not found!");
			throw e;
		}
	}

	@Test(expected = RoomServiceException.class)
	public void testFindByNullRoomType() {
		RoomRepo repo = mock(RoomRepo.class);
		RoomServiceImpl service = new RoomServiceImpl(repo, roomTypes);
		verify(repo, times(0)).findRoomsByRoomType(any());
		try {
			service.findRoomsByType(null);
		} catch (RoomServiceException e) {
			assertThat(e.getMessage()).isEqualTo("Room type: null not found!");
			throw e;
		}
	}

}
