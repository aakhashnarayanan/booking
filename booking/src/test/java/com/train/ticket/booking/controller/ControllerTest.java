package com.train.ticket.booking.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.train.ticket.booking.pojo.Receipt;
import com.train.ticket.booking.pojo.Train;
import com.train.ticket.booking.pojo.User;

class ControllerTest {

	private Controller controller;
	private Train train;

	@BeforeEach
	public void setUp() {
		controller = new Controller();
		train = mock(Train.class);
		controller.setTrain(train);
	}

	@Test
	public void testAddNewTicketSuccess() {
		User user = new User("Aakhash", "S", "aakhash@gmail.com");
		when(train.allocateTicket(Mockito.any())).thenReturn(true);

		ResponseEntity<String> response = controller.addNewTicket(user);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(response.getBody().contains("Ticket purchased successfully"));
	}
	
	@Test
	public void testAddNewTicketFailureUserExits() {
		User user = new User("Aakhash", "S", "aakhash@gmail.com");
		when(train.isUserPresent(user.getEmailId())).thenReturn(true);

		ResponseEntity<String> response = controller.addNewTicket(user);

		assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
		assertTrue(response.getBody().contains("User Already alloted for Seat"));
	}

	@Test
	public void testAddNewTicketFailure() {
		User user = new User("Aakhash", "S", "aakhash@gmail.com");
		when(train.allocateTicket(Mockito.any())).thenReturn(false);

		ResponseEntity<String> response = controller.addNewTicket(user);

		assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
		assertTrue(response.getBody().contains("Train is Filled"));
	}

	@Test
	public void testGetReceipt() {
		String emailId = "aakhash@gmail.com";
		Receipt receipt = new Receipt("Section: A Seat Number: 1", new User("Aakhash", "S", emailId));
		when(train.getReceipt(emailId)).thenReturn(receipt);

		ResponseEntity<String> response = controller.getReceipt(emailId);

		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		assertTrue(response.getBody().contains("Receipt Details"));
	}

	@Test
	public void testGetReceiptInvalidEmailId() {
		String emailId = "aakhash@gmail.com";
		when(train.getReceipt(emailId)).thenReturn(null);

		ResponseEntity<String> response = controller.getReceipt(emailId);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("User is not available"));
	}

	@Test
	public void testGetSectionDetailsInvalidSection() {
		String section = "C";

		ResponseEntity<String> response = controller.getSectionDetails(section);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Invalid Section"));
	}

	@Test
	public void testRemoveUserSuccess() {
		String emailId = "aakhash@gmail.com";
		when(train.removeUser(emailId)).thenReturn(true);

		ResponseEntity<String> response = controller.removeUser(emailId);

		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		assertTrue(response.getBody().contains("User deleted successfully"));
	}

	@Test
	public void testRemoveUserFailure() {
		String emailId = "aakhash@gmail.com";
		when(train.removeUser(emailId)).thenReturn(false);

		ResponseEntity<String> response = controller.removeUser(emailId);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("User is not Available"));
	}

	@Test
	public void testUpdateSeatSuccess() {
		String emailId = "aakhash@gmail.com";
		int seatNum = 3;
		when(train.updateSeat(seatNum, emailId)).thenReturn("Updated the Seat");

		ResponseEntity<String> response = controller.updateSeat(seatNum, emailId);

		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		assertTrue(response.getBody().contains("Updated the Seat"));
	}

	@Test
	public void testUpdateSeatInvalidSeat() {
		String emailId = "aakhash@gmail.com";
		int seatNum = 5;
		when(train.updateSeat(seatNum, emailId)).thenReturn("Invalid seat Number");

		ResponseEntity<String> response = controller.updateSeat(seatNum, emailId);

		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		assertTrue(response.getBody().contains("Invalid seat Number"));
	}

}
