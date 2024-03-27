package com.train.ticket.booking.pojo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainTest {

	private Train train;

	@BeforeEach
	public void setUp() {
		train = new Train();
	}

	@Test
	public void testAllocateTicket() {
		User user = new User("Aakhash", "S", "aakhash@gmail.com");
		Ticket ticket = new Ticket(user);
		assertTrue(train.allocateTicket(ticket));
	}

	@Test
	public void testRemoveUser() {
		User user = new User("Aakhash", "S", "aakhash@gmail.com");
		Ticket ticket = new Ticket(user);
		train.allocateTicket(ticket);
		assertTrue(train.removeUser("aakhash@gmail.com"));
	}

	@Test
	public void testUpdateSeat() {
		User user = new User("Aakhash", "S", "aakhash@gmail.com");
		Ticket ticket = new Ticket(user);
		train.allocateTicket(ticket);
		assertTrue(train.updateSeat(3, "aakhash@gmail.com").contains("Updated the Seat"));
	}

	@Test
	public void testGetReceipt() {
		User user = new User("Aakhash", "S", "aakhash@gmail.com");
		Ticket ticket = new Ticket(user);
		train.allocateTicket(ticket);
		Receipt receipt = train.getReceipt("aakhash@gmail.com");
		assertNotNull(receipt);
		assertEquals("Section: A Seat Number: 1", receipt.getSeating());
	}

	@Test
	public void testGetSectionDetails() {
		assertTrue(train.getSections().get("A").toString().contains("Section"));
	}

}
