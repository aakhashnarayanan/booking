package com.train.ticket.booking.pojo;

public class Receipt {

	public String getSeating() {
		return seating;
	}

	public User getUser() {
		return user;
	}

	public String getDestination() {
		return destination;
	}

	public double getPrice() {
		return price;
	}

	private String seating;
	private User user;
	private String source = "London";
	private String destination = "France";
	private double price = 20;

	public Receipt(String seating, User user) {
		this.seating = seating;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Receipt [seating=" + seating + ", user=" + user + ", source=" + source + ", destination=" + destination
				+ ", price=" + price + "$ ]";
	}

}
