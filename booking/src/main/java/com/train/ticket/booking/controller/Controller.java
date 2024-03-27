package com.train.ticket.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.train.ticket.booking.pojo.Receipt;
import com.train.ticket.booking.pojo.Ticket;
import com.train.ticket.booking.pojo.Train;
import com.train.ticket.booking.pojo.User;

@RestController
@RequestMapping("/train")
public class Controller {

	Train train = new Train();

	@PostMapping("/newticket")
	public ResponseEntity<String> addNewTicket(@RequestBody User user) {
		Ticket ticket = new Ticket(user);
		if(train.isUserPresent(user.getEmailId())) {
			return new ResponseEntity<>("User Already alloted for Seat " + user, HttpStatus.EXPECTATION_FAILED);
		}
		if (!train.allocateTicket(ticket)) {
			return new ResponseEntity<>("Train is Filled " + user, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>("Ticket purchased successfully: " + user + " for 20$", HttpStatus.CREATED);
	}

	public Controller() {

	}

	public void setTrain(Train train) {
		this.train = train;
	}

	@GetMapping("/receipt")
	public ResponseEntity<String> getReceipt(@RequestParam("emailId") String emailId) {
		Receipt receipt = train.getReceipt(emailId);
		if (receipt == null) {
			return new ResponseEntity<>("User is not available", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Receipt Details: " + receipt, HttpStatus.FOUND);
	}

	@GetMapping("/section/{section}")
	public ResponseEntity<String> getSectionDetails(@PathVariable("section") String section) {
		if (section.equals("A") || section.equals("B")) {
			return new ResponseEntity<>("Seat details" + train.getSections().get(section).getSeats(), HttpStatus.FOUND);
		}
		return new ResponseEntity<>("Invalid Section :" + section + " *Section should be either A or B*",
				HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> removeUser(@RequestParam("emailId") String emailId) {
		if (train.removeUser(emailId)) {
			return new ResponseEntity<>("User deleted successfully for emailId: " + emailId, HttpStatus.FOUND);
		}
		return new ResponseEntity<>("User is not Available for emailId:" + emailId, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/updateseat")
	public ResponseEntity<String> updateSeat(@RequestParam("seatNum") int seatNum,
			@RequestParam("emailId") String emailId) {
		return new ResponseEntity<>(train.updateSeat(seatNum, emailId), HttpStatus.FOUND);
	}
}
