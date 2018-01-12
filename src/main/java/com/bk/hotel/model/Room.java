package com.bk.hotel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Room {
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "room_number")
	private String roomNumber;
	@Column(name = "room_type")
	private String roomType;
	@Column(name = "room_rate")
	private BigDecimal roomRate;

	public Room() {
	}

	public Room(long id, String roomNumber, String roomType, BigDecimal roomRate) {
		this.id = id;
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.roomRate = roomRate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public BigDecimal getRoomRate() {
		return roomRate;
	}

	public void setRoomRate(BigDecimal roomRate) {
		this.roomRate = roomRate;
	}

}
