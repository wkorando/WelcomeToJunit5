package com.bk.hotel.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.bk.hotel.RoomServiceException;
import com.bk.hotel.model.Room;
import com.bk.hotel.repo.RoomRepo;

@DemoGodsDemandSacrifice
public class RoomServiceImplJunit5Test {
	private List<String> roomTypes = Arrays.asList("Single", "Double", "Suite");

	@Test
	public void testFindByValidRoomType() {
		RoomRepo repo = mock(RoomRepo.class);
		RoomServiceImpl service = new RoomServiceImpl(repo, roomTypes);
		when(repo.findRoomsByRoomType("Single")).thenReturn(Arrays.asList(//
				new Room(1L, "100", "Single", new BigDecimal(145.99))));
		List<Room> rooms = service.findRoomsByType("Single");

		assertEquals(1, rooms.size());
	}

	@Test
	public void testFindByInvalidRoomType() {
		RoomRepo repo = mock(RoomRepo.class);
		RoomServiceImpl service = new RoomServiceImpl(repo, roomTypes);
		verify(repo, times(0)).findRoomsByRoomType(any());
		RoomServiceException e = assertThrows(RoomServiceException.class, () -> service.findRoomsByType("NOT FOUND"));
		assertEquals("Room type: NOT FOUND not found!", e.getMessage());
	}

	@Test
	public void testFindByNullRoomType() {
		RoomRepo repo = mock(RoomRepo.class);
		RoomServiceImpl service = new RoomServiceImpl(repo, roomTypes);
		verify(repo, times(0)).findRoomsByRoomType(any());
		RoomServiceException e = assertThrows(RoomServiceException.class, () -> service.findRoomsByType(null));
		assertEquals("Room type: null not found!", e.getMessage());

	}

	@Test
	public void testAddRoom() {
		RoomRepo repo = mock(RoomRepo.class);
		when(repo.save(any())).thenReturn(new Room(1L, "100", "Single", new BigDecimal(149.99)));
		RoomServiceImpl service = new RoomServiceImpl(repo, roomTypes);

		Room newRoom = service.addRoom(new Room());
		assertAll(() -> assertEquals(1L, newRoom.getId()), 
				() -> assertEquals("100", newRoom.getRoomNumber()),
				() -> assertEquals("Single", newRoom.getRoomType()),
				() -> assertEquals(new BigDecimal(149.99), newRoom.getRoomRate()));
	}
	
	@Test
	public void testAutowiring(TestInfo testInfo) {
		System.out.println(testInfo);
	}

}
