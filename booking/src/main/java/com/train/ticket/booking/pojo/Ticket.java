package com.train.ticket.booking.pojo;

public class Ticket {

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	private String source = "London";
	private String destination = "France";
	private User user;
	private double price = 20;

	public Ticket(User user) {
		this.user = user;
	}
}