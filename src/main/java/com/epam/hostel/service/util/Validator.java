package com.epam.hostel.service.util;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.bean.entity.ScheduleRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ASUS on 19.10.2016.
 */
public class Validator {

    private static final String LOGIN_REGEXP = "^[a-zA-Z][a-zA-Z0-9_]{4,39}$";
    private static final String PASSWORD_REGEXP = "(?=^.{6,45}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private static final String PASSPORT_SERIES_REGEXP = "^[A-Z]{2}$";
    private static final String NAME_REGEXP = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]{2,40}$";
    private static final String DATE_REGEXP = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    public static boolean validateInt(int value) {
        return value > 0;
    }

    public static boolean validateString(String value, int maxLength) {
        if (value == null || value.isEmpty() || value.length() > maxLength)
            return false;
        else return true;
    }

    public static boolean validateString(String value) {
        if (value == null || value.isEmpty())
            return false;
        return true;
    }

    public static boolean validateDiscountValue(int value){
        return validateInt(value) && value <=1000;
    }

    public static boolean validateRoomNumber(int number){
        return number >= 100 && number <= 1000;
    }

    public static boolean validatePerdayCost(int perdayCost){
        return perdayCost >= 10 && perdayCost <= 100;
    }

    public static boolean validateLogin(String login) {
        if (!validateString(login)) {
            return false;
        }
        Pattern pattern = Pattern.compile(LOGIN_REGEXP);
        Matcher m = pattern.matcher(login);
        return m.matches();
    }

    public static boolean validatePassword(String password) {
        if (!validateString(password)) {
            return false;
        }
        Pattern pattern = Pattern.compile(PASSWORD_REGEXP);
        Matcher m = pattern.matcher(password);
        return m.matches();
    }

    public static boolean validatePassportIdNumber(int identificationNumber) {
        if ((identificationNumber < 1000000) || (identificationNumber > 9999999)) {
            return false;
        }
        return true;
    }

    public static boolean validatePassportSeries(String series) {
        if (!validateString(series)) {
            return false;
        }
        Pattern pattern = Pattern.compile(PASSPORT_SERIES_REGEXP);
        Matcher m = pattern.matcher(series);
        return m.matches();
    }

    public static boolean validateName(String name) {
        if (!validateString(name)) {
            return false;
        }
        Pattern pattern = Pattern.compile(NAME_REGEXP);
        Matcher m = pattern.matcher(name);
        return m.matches();
    }

    public static boolean validateBirthdayDate(Date birthday) {
        if (birthday == null) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date;
        try {
            date = format.format(birthday);
        } catch (Exception e) {
            return false;
        }
        Pattern pattern = Pattern.compile(DATE_REGEXP);
        Matcher m = pattern.matcher(date);
        return m.matches();
    }

    public static boolean validateDate(Date date){
        return true;
    }

    public static boolean validateDaysStayNumber(int daysStayNumber) {
        return validateInt(daysStayNumber) && daysStayNumber < 31;
    }

    public static boolean validateSeatsNumber(int seatsNumber) {
        return validateInt(seatsNumber) && seatsNumber < 6;
    }

    public static boolean validatePayment(Room room, Discount discount, RentalRequest rentalRequest, ScheduleRecord scheduleRecord) {
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

    public static boolean validateRoom(Room room, int seatsNumber) {
        return room != null && room.getSeatsNumber() == seatsNumber;
    }

    public static boolean validateDiscount(Discount discount, int clientId){
        return discount != null && discount.getClientId() == clientId;
    }
}
