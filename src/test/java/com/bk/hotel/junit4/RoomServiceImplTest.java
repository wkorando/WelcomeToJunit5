package com.bk.hotel.junit4;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.bk.hotel.RoomServiceException;
import com.bk.hotel.model.Room;
import com.bk.hotel.repo.RoomRepo;
import com.bk.hotel.service.impl.RoomServiceImpl;

public class RoomServiceImplTest {
	private List<String> roomTypes = Arrays.asList("Single", "Double", "Suite");
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

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
		expectedException.expect(RoomServiceException.class);
		expectedException.expectMessage("Room type: NOT FOUND not found!");
		service.findRoomsByType("NOT FOUND");
	}

	@Test(expected = RoomServiceException.class)
	public void testFindByNullRoomType() {
		RoomRepo repo = mock(RoomRepo.class);
		RoomServiceImpl service = new RoomServiceImpl(repo, roomTypes);
		verify(repo, times(0)).findRoomsByRoomType(any());
		try {
			service.findRoomsByType(null);
		} catch (RoomServiceException e) {
			assertEquals("Room type: null not found!", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testAddRoom() {
		RoomRepo repo = mock(RoomRepo.class);
		when(repo.save(any())).thenReturn(new Room(1L, "100", "Single", new BigDecimal(149.99)));
		RoomServiceImpl service = new RoomServiceImpl(repo, roomTypes);

		Room newRoom = service.addRoom(new Room());
		assertEquals(1L, newRoom.getId());
		assertEquals("100", newRoom.getRoomNumber());
		assertEquals("Single", newRoom.getRoomType());
		assertEquals(new BigDecimal(149.99), newRoom.getRoomRate());
	}

}
