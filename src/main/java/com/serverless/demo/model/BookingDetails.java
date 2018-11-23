package com.serverless.demo.model;

public class BookingDetails {
    private String ticketId;
    private String city;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BookingDetails() {
    }

    public BookingDetails(String ticketId, String city) {
        this.ticketId = ticketId;
        this.city = city;
    }
}
