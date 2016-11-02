package com.epam.hostel.bean.entity;

/**
 * Created by ASUS on 02.11.2016.
 */
public class Room {

    private int number;
    private int seatsNumber;
    private int perdayCost;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public int getPerdayCost() {
        return perdayCost;
    }

    public void setPerdayCost(int perdayCost) {
        this.perdayCost = perdayCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (number != room.number) return false;
        if (seatsNumber != room.seatsNumber) return false;
        if (perdayCost != room.perdayCost) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + seatsNumber;
        result = 31 * result + perdayCost;
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", seatsNumber=" + seatsNumber +
                ", perdayCost=" + perdayCost +
                '}';
    }
}
