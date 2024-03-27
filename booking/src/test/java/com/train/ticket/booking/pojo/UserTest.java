package com.train.ticket.booking.pojo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserTest {

	@Test
	public void testGetters() {
		User user = new User("Aakhash", "S", "aakhash@gmail.com");
		assertEquals("Aakhash", user.getFirstName());
		assertEquals("S", user.getLastName());
		assertEquals("aakhash@gmail.com", user.getEmailId());
	}

	@Test
	public void testDefaultConstructor() {
		User user = new User();
		assertNull(user.getFirstName());
		assertNull(user.getLastName());
		assertNull(user.getEmailId());
	}

	@Test
	public void testSetters() {
		User user = new User();
		String firstName = "Aakhash";
		String lastName = "S";
		String emailId = "aakhash@gmail.com";
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmailId(emailId);
		assertEquals(firstName, user.getFirstName());
		assertEquals(lastName, user.getLastName());
		assertEquals(emailId, user.getEmailId());
	}

	@Test
	public void testToString() {
		String firstName = "Aakhash";
		String lastName = "S";
		String emailId = "aakhash@gmail.com";
		String expectedToString = "User [firstName=Aakhash, lastName=S, emailId=aakhash@gmail.com]";
		User user = new User(firstName, lastName, emailId);
		assertEquals(expectedToString, user.toString());
	}
}
