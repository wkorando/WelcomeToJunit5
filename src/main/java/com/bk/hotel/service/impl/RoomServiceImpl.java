package com.bk.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bk.hotel.RoomServiceException;
import com.bk.hotel.model.Room;
import com.bk.hotel.repo.RoomRepo;
import com.bk.hotel.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	private RoomRepo roomRepo;
	private List<String> roomTypes;

	public RoomServiceImpl(RoomRepo roomRepo, List<String> roomTypes) {
		this.roomRepo = roomRepo;
		this.roomTypes = roomTypes;
	}

	@Override
	public List<Room> findRoomsByType(String type) {
		if (roomTypes.contains(type)) {
			return roomRepo.findRoomsByRoomType(type);
		} else {
			throw new RoomServiceException("Room type: " + type + " not found!");
		}

	}

	@Override
	public List<Room> findRoomsByFloor(String floor) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Room addRoom(Room room) {
		List<String> errorMessages = new ArrayList<>();
		if(roomRepo.findByRoomNumber(room.getRoomNumber()) != null) {
			errorMessages.add("Duplicated room number!");
		}
		return roomRepo.save(room);
	}

}
