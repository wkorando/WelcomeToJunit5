package com.bk.hotel.service;

import java.util.List;

import com.bk.hotel.model.Room;

public interface RoomService {
	List<Room> findRoomsByType(String type);

	List<Room> findRoomsByFloor(String floor);
}
