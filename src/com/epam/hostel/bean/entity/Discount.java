package com.epam.hostel.bean.entity;

/**
 * Created by ASUS on 15.11.2016.
 */
public class Discount {

    private int id;
    private int clientId;
    private int value;
    private int administratorId;
    private boolean used;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(int administratorId) {
        this.administratorId = administratorId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discount discount = (Discount) o;

        if (id != discount.id) return false;
        if (clientId != discount.clientId) return false;
        if (value != discount.value) return false;
        if (administratorId != discount.administratorId) return false;
        if (used != discount.used) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + clientId;
        result = 31 * result + value;
        result = 31 * result + administratorId;
        result = 31 * result + (used ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", value=" + value +
                ", administratorId=" + administratorId +
                ", used=" + used +
                '}';
    }
}
