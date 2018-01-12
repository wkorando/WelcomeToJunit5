package com.bk.hotel.service;

import java.util.List;

public interface ReservationService {
	List<String> verifyReservationDates(String startDate, String endDate);
}
