package com.bk.hotel.model;

import java.util.Date;

public class Reservation {
	private long id;
	private long customerId;
	private long roomId;
	private Date startDate;
	private Date endDate;

	public Reservation() {
	}

	public Reservation(long id, long customerId, long roomId, Date startDate, Date endDate) {
		this.id = id;
		this.customerId = customerId;
		this.roomId = roomId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
