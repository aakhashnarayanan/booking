package com.train.ticket.booking.pojo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class Train {

	public Train() {
		seatNumberList.addAll(IntStream.rangeClosed(1, 40).boxed().toList());
		//Assume each section has 20 seats
	}

	private Map<String, Section> sections = new HashMap<>() {
		{
			put("A", new Section());
			put("B", new Section());
		}
	};

	TreeSet<Integer> seatNumberList = new TreeSet<>();

	public boolean allocateTicket(Ticket ticket) {
		if (!seatNumberList.isEmpty() && seatNumberList.getFirst() <= 20
				&& this.sections.get("A").getSeats().size() < 20) {
			this.sections.get("A").allocateSeat(seatNumberList.getFirst(), ticket.getUser());
			seatNumberList.removeFirst();
		} else if (!seatNumberList.isEmpty() && seatNumberList.getFirst() <= 40
				&& this.sections.get("B").getSeats().size() < 20) {
			this.sections.get("B").allocateSeat(seatNumberList.getFirst(), ticket.getUser());
			seatNumberList.removeFirst();
		} else {
			return false;
		}
		return true;
	}
	
	public boolean isUserPresent(String emailId) {
		if(getSeatNumberByEmailAndSection(emailId,"A")!=0) {
			return true;
		}
		if(getSeatNumberByEmailAndSection(emailId,"B")!=0) {
			return true;
		}
		return false;
	}

	public boolean removeUser(String emailId) {
		int seatNum = getSeatNumberByEmailAndSection(emailId, "A");
		if (seatNum != 0) {
			this.sections.get("A").getSeats().remove(seatNum);
			seatNumberList.add(seatNum);
			return true;
		}
		seatNum = getSeatNumberByEmailAndSection(emailId, "B");
		if (seatNum != 0) {
			this.sections.get("B").getSeats().remove(seatNum);
			seatNumberList.add(seatNum);
			return true;
		}
		return false;
	}

	public Receipt getReceipt(String emailId) {
		int seatNum = getSeatNumberByEmailAndSection(emailId, "A");
		String seating = "";
		User user = new User();
		if (seatNum != 0) {
			seating = "Section: A Seat Number: " + seatNum;
			user = this.sections.get("A").getSeats().get(seatNum);
			Receipt receipt = new Receipt(seating, user);
			return receipt;
		}
		seatNum = (seatNum != 0) ? seatNum : getSeatNumberByEmailAndSection(emailId, "B");
		if (seatNum != 0) {
			seating = "Section: B Seat Number: " + seatNum;
			user = this.sections.get("B").getSeats().get(seatNum);
			Receipt receipt = new Receipt(seating, user);
			return receipt;
		}
		return null;
	}

	public int getSeatNumberByEmailAndSection(String emailId, String Section) {
		Iterator<Map.Entry<Integer, User>> iterator = this.sections.get(Section).getSeats().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, User> entry = iterator.next();
			if (entry.getValue().getEmailId().equals(emailId)) {
				return entry.getKey();
			}
		}
		return 0;
	}

	public String updateSeat(int newSeatNum, String emailId) {
		int seatNum;
		seatNum = (getSeatNumberByEmailAndSection(emailId, "A") != 0) ? (getSeatNumberByEmailAndSection(emailId, "A"))
				: (getSeatNumberByEmailAndSection(emailId, "B"));
		if (seatNum == 0) {
			return "User is Not present";
		}
		if (newSeatNum > 40) {
			return "Invalid seat Number Seat Number ranges from 1 to 40";
		}
		if (isSeatAvailable(newSeatNum)) {
			return "Seat is already filled";
		}
		if (seatNum <= 20) {
			User user = this.sections.get("A").getSeats().get(seatNum);
			this.sections.get("A").getSeats().remove(seatNum);
			seatNumberList.add(seatNum);
			Ticket ticket = new Ticket(user);
			updateNewSeat(ticket, newSeatNum);
		} else if (seatNum > 20 && seatNum <= 40) {
			User user = this.sections.get("B").getSeats().get(seatNum);
			this.sections.get("B").getSeats().remove(seatNum);
			seatNumberList.add(seatNum);
			Ticket ticket = new Ticket(user);
			updateNewSeat(ticket, newSeatNum);
		}
		return "Updated the Seat";
	}

	public void updateNewSeat(Ticket ticket, int newSeatNum) {
		if (newSeatNum <= 20) {
			this.sections.get("A").allocateSeat(newSeatNum, ticket.getUser());
			seatNumberList.remove(newSeatNum);
		} else if (newSeatNum > 20 && newSeatNum <= 40) {
			this.sections.get("B").allocateSeat(newSeatNum, ticket.getUser());
			seatNumberList.remove(newSeatNum);
		}
	}

	public boolean isSeatAvailable(int seatNum) {
		String section;
		section = (seatNum <= 20) ? "A" : "B";
		return this.sections.get(section).getSeats().containsKey(seatNum);
	}

	public Map<String, Section> getSections() {
		return sections;
	}

	public void setSections(Map<String, Section> sections) {
		this.sections = sections;
	}

	@Override
	public String toString() {
		return "Train [sections=" + sections + "]";
	}
}