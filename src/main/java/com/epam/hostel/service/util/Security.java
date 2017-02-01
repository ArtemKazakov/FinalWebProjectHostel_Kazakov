package com.epam.hostel.service.util;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.bean.entity.ScheduleRecord;

/**
 * Makes security check of all input data.
 */
public class Security {
    /**
     * Checks room for seats number;
     * @param room a checking room
     * @param seatsNumber an expected seats number
     * @return {@code true} if seats numbers are equals and {@code false} otherwise
     */
    public static boolean checkRoom(Room room, int seatsNumber) {
        return room != null && room.getSeatsNumber() == seatsNumber;
    }

    /**
     * Checks discount for client id.
     * @param discount a checking discount
     * @param clientId an expected seats client id
     * @return {@code true} if clients id are equals and {@code false} otherwise
     */
    public static boolean checkDiscount(Discount discount, int clientId){
        return discount == null || discount.getClientId() == clientId;
    }

    /**
     * Checks payment data.
     * @param room a room that client has rent
     * @param discount a discount that client used
     * @param rentalRequest a rental request
     * @param scheduleRecord a schedule record
     * @return {@code true} if payment isn`t right and {@code false} otherwise
     */
    public static boolean checkPayment(Room room, Discount discount, RentalRequest rentalRequest, ScheduleRecord scheduleRecord) {
        if(room == null){
            return false;
        }

        int payment = 0;
        payment = rentalRequest.getDaysStayNumber() * room.getPerdayCost();
        if(!rentalRequest.isFullPayment()){
            payment *= 1.1;
            payment = Math.round(payment);
        }

        if (discount != null) {
            payment -= discount.getValue();
        }

        if (payment < 0){
            payment = 0;
        }

        if (rentalRequest.isFullPayment()){
            if (payment != rentalRequest.getPayment()){
                return false;
            }
        } else {
            if (payment != scheduleRecord.getPaymentDuty()){
                return false;
            }
        }

        return true;
    }
}
