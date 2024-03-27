package com.train.ticket.booking.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Section {

	Map<Integer, User> seats;

	public Section() {
		seats = new TreeMap<>();
	}

	public boolean allocateSeat(int seatNumber, User user) {
		if (seats.containsKey(seatNumber)) {
			return false;
		}
		seats.put(seatNumber, user);
		return true;
	}

	public Map<Integer, User> getSeats() {
		return seats;
	}

	@Override
	public String toString() {
		return "Section [seats=" + seats + "]";
	}
}