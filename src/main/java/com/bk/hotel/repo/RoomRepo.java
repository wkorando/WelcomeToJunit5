package com.bk.hotel.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bk.hotel.model.Room;

public interface RoomRepo extends CrudRepository<Room, Long> {

	List<Room> findRoomsByRoomType(String type);

	Room findByRoomNumber(String roomNumber);

}
