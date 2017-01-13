package com.epam.hostel.bean.entity;

import java.util.Date;

/**
 * Created by ASUS on 15.11.2016.
 */
public class ScheduleRecord {

    private int id;
    private int roomNumber;
    private int requestId;
    private Date checkInDate;
    private Date checkoutDate;
    private int paymentDuty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public int getPaymentDuty() {
        return paymentDuty;
    }

    public void setPaymentDuty(int paymentDuty) {
        this.paymentDuty = paymentDuty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleRecord that = (ScheduleRecord) o;

        if (id != that.id) return false;
        if (roomNumber != that.roomNumber) return false;
        if (requestId != that.requestId) return false;
        if (paymentDuty != that.paymentDuty) return false;
        if (checkInDate != null ? !checkInDate.equals(that.checkInDate) : that.checkInDate != null) return false;
        if (checkoutDate != null ? !checkoutDate.equals(that.checkoutDate) : that.checkoutDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + roomNumber;
        result = 31 * result + requestId;
        result = 31 * result + (checkInDate != null ? checkInDate.hashCode() : 0);
        result = 31 * result + (checkoutDate != null ? checkoutDate.hashCode() : 0);
        result = 31 * result + paymentDuty;
        return result;
    }

    @Override
    public String toString() {
        return "ScheduleRecord{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", requestId=" + requestId +
                ", checkInDate=" + checkInDate +
                ", checkoutDate=" + checkoutDate +
                ", paymentDuty=" + paymentDuty +
                '}';
    }
}
