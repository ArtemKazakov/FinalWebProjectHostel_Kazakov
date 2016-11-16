package com.epam.hostel.bean.entity;

import java.util.Date;

/**
 * Created by ASUS on 15.11.2016.
 */
public class Request {

    private int id;
    private int clientId;
    private int administratorId;
    private int seatsNumber;
    private Date checkinDate;
    private int daysStayNumber;
    private boolean fullPay;
    private int payment;
    private boolean accepted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(int administratorId) {
        this.administratorId = administratorId;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public int getDaysStayNumber() {
        return daysStayNumber;
    }

    public void setDaysStayNumber(int daysStayNumber) {
        this.daysStayNumber = daysStayNumber;
    }

    public boolean isFullPay() {
        return fullPay;
    }

    public void setFullPay(boolean fullPay) {
        this.fullPay = fullPay;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (id != request.id) return false;
        if (clientId != request.clientId) return false;
        if (administratorId != request.administratorId) return false;
        if (seatsNumber != request.seatsNumber) return false;
        if (daysStayNumber != request.daysStayNumber) return false;
        if (fullPay != request.fullPay) return false;
        if (payment != request.payment) return false;
        if (accepted != request.accepted) return false;
        if (checkinDate != null ? !checkinDate.equals(request.checkinDate) : request.checkinDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + clientId;
        result = 31 * result + administratorId;
        result = 31 * result + seatsNumber;
        result = 31 * result + (checkinDate != null ? checkinDate.hashCode() : 0);
        result = 31 * result + daysStayNumber;
        result = 31 * result + (fullPay ? 1 : 0);
        result = 31 * result + payment;
        result = 31 * result + (accepted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", administratorId=" + administratorId +
                ", seatsNumber=" + seatsNumber +
                ", checkinDate=" + checkinDate +
                ", daysStayNumber=" + daysStayNumber +
                ", fullPay=" + fullPay +
                ", payment=" + payment +
                ", accepted=" + accepted +
                '}';
    }
}
