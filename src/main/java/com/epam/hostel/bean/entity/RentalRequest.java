package com.epam.hostel.bean.entity;

import java.util.Date;

/**
 * Created by ASUS on 15.11.2016.
 */
public class RentalRequest {

    private int id;
    private User client;
    private User administrator;
    private int seatsNumber;
    private Date checkInDate;
    private int daysStayNumber;
    private boolean fullPayment;
    private int payment;
    private Boolean accepted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public int getDaysStayNumber() {
        return daysStayNumber;
    }

    public void setDaysStayNumber(int daysStayNumber) {
        this.daysStayNumber = daysStayNumber;
    }

    public boolean isFullPayment() {
        return fullPayment;
    }

    public void setFullPayment(boolean fullPayment) {
        this.fullPayment = fullPayment;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentalRequest rentalRequest = (RentalRequest) o;

        if (id != rentalRequest.id) return false;
        if (client != rentalRequest.client) return false;
        if (administrator != rentalRequest.administrator) return false;
        if (seatsNumber != rentalRequest.seatsNumber) return false;
        if (daysStayNumber != rentalRequest.daysStayNumber) return false;
        if (fullPayment != rentalRequest.fullPayment) return false;
        if (payment != rentalRequest.payment) return false;
        if (accepted != rentalRequest.accepted) return false;
        if (checkInDate != null ? !checkInDate.equals(rentalRequest.checkInDate) : rentalRequest.checkInDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (administrator != null ? administrator.hashCode() : 0);
        result = 31 * result + seatsNumber;
        result = 31 * result + (checkInDate != null ? checkInDate.hashCode() : 0);
        result = 31 * result + daysStayNumber;
        result = 31 * result + (fullPayment ? 1 : 0);
        result = 31 * result + payment;
        result = 31 * result + (accepted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", client=" + client +
                ", administrator=" + administrator +
                ", seatsNumber=" + seatsNumber +
                ", checkInDate=" + checkInDate +
                ", daysStayNumber=" + daysStayNumber +
                ", fullPayment=" + fullPayment +
                ", payment=" + payment +
                ", accepted=" + accepted +
                '}';
    }
}
