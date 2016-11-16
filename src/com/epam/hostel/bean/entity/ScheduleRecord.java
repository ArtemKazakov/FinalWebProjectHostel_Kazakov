package com.epam.hostel.bean.entity;

import java.util.Date;

/**
 * Created by ASUS on 15.11.2016.
 */
public class ScheduleRecord {

    private int id;
    private int roomId;
    private int requestId;
    private Date chekinDate;
    private Date checkoutDate;
    private int paymentDuty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Date getChekinDate() {
        return chekinDate;
    }

    public void setChekinDate(Date chekinDate) {
        this.chekinDate = chekinDate;
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
        if (roomId != that.roomId) return false;
        if (requestId != that.requestId) return false;
        if (paymentDuty != that.paymentDuty) return false;
        if (chekinDate != null ? !chekinDate.equals(that.chekinDate) : that.chekinDate != null) return false;
        if (checkoutDate != null ? !checkoutDate.equals(that.checkoutDate) : that.checkoutDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + roomId;
        result = 31 * result + requestId;
        result = 31 * result + (chekinDate != null ? chekinDate.hashCode() : 0);
        result = 31 * result + (checkoutDate != null ? checkoutDate.hashCode() : 0);
        result = 31 * result + paymentDuty;
        return result;
    }

    @Override
    public String toString() {
        return "ScheduleRecord{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", requestId=" + requestId +
                ", chekinDate=" + chekinDate +
                ", checkoutDate=" + checkoutDate +
                ", paymentDuty=" + paymentDuty +
                '}';
    }
}
