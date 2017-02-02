package com.epam.hostel.service.util;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.ScheduleRecord;

import java.nio.CharBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Makes validation of all input data.
 */
public class Validator {

    private static final String LOGIN_REGEXP = "^[a-zA-Z][a-zA-Z0-9_]{4,39}$";
    private static final String PASSWORD_REGEXP = "(?=^.{6,45}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private static final String PASSPORT_SERIES_REGEXP = "^[A-Z]{2}$";
    private static final String NAME_REGEXP = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]{2,40}$";
    private static final String DATE_REGEXP = "(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String BIRTHDAY_DATE_MAX = "1999-01-01";
    private static final String BIRTHDAY_DATE_MIN = "1919-12-31";

    private static final int MAX_DISCOUNT_VALUE = 1000;
    private static final int MAX_ROOM_NUMBER = 1000;
    private static final int MAX_PER_DAY_COST = 100;
    private static final int MAX_SEATS_NUMBER = 5;
    private static final int MAX_DAYS_STAY_NUMBER = 31;
    private static final int MIN_PASSPORT_ID = 1000000;
    private static final int MAX_PASSPORT_ID = 9999999;

    /**
     * Checks {@link int} for more than zero.
     *
     * @param value a {@link int} value
     * @return {@code true} if value is  more than 0 and {@code false} otherwise
     */
    public static boolean validateInt(int value) {
        return value > 0;
    }

    /**
     * Checks {@link int} for more or equal than zero.
     *
     * @param value a {@link int} value
     * @return {@code true} if value is  more or equal than 0 and {@code false} otherwise
     */
    public static boolean validateStart(int value) { return value >= 0;}

    /**
     * Checks string for null value and length.
     *
     * @param value a given string
     * @return {@code true} if value is not null and length more than 0 , and {@code false} otherwise
     */
    public static boolean validateString(String value) {
        return !(value == null || value.isEmpty());
    }

    /**
     * Checks discount value.
     *     * @param value a discount value
     * @return {@code true} if value is valid, and {@code false} otherwise
     */
    public static boolean validateDiscountValue(int value) {
        return validateInt(value) && value <= MAX_DISCOUNT_VALUE;
    }

    /**
     * Checks room number.
     *
     * @param number a room number
     * @return {@code true} if room number is valid, and {@code false} otherwise
     */
    public static boolean validateRoomNumber(int number) {
        return number >= 100 && number <= MAX_ROOM_NUMBER;
    }

    /**
     * Checks room per day cost.
     *
     * @param perdayCost a per day cost
     * @return {@code true} if per day cost is valid, and {@code false} otherwise
     */
    public static boolean validatePerdayCost(int perdayCost) {
        return perdayCost >= 10 && perdayCost <= MAX_PER_DAY_COST;
    }

    /**
     * Checks the given string for compliance with the
     * {@link Validator#LOGIN_REGEXP} pattern
     *
     * @param login string for check
     * @return {@code true} if given string
     * is matches to the pattern and {@code false} otherwise
     */
    public static boolean validateLogin(String login) {
        if (!validateString(login)) {
            return false;
        }
        Pattern pattern = Pattern.compile(LOGIN_REGEXP);
        Matcher m = pattern.matcher(login);
        return m.matches();
    }

    /**
     * Checks the given byte array for compliance with the
     * {@link Validator#PASSWORD_REGEXP} pattern
     *
     * @param password byte array for check
     * @return {@code true} if given byte array
     * is matches to the pattern and {@code false} otherwise
     */
    public static boolean validatePassword(byte[] password) {
        if (password == null || password.length < 1) {
            PasswordHelper.dispose(password);
            return false;
        }
        char[] chars = new char[password.length];
        for (int i = 0; i < password.length; i++) {
            chars[i] = (char) password[i];
        }

        Pattern pattern = Pattern.compile(PASSWORD_REGEXP);
        Matcher m = pattern.matcher(CharBuffer.wrap(chars));

        boolean match = m.matches();

        if (!match) {
            PasswordHelper.dispose(password);
        }
        for (int i = 0; i < chars.length; i++) {
            chars[i] = 0;
        }
        return match;
    }

    /**
     * Checks passport id number.
     *
     * @param identificationNumber a passport id number
     * @return {@code true} if passport id number is valid, and {@code false} otherwise
     */
    public static boolean validatePassportIdNumber(int identificationNumber) {
        return !((identificationNumber < MIN_PASSPORT_ID) || (identificationNumber > MAX_PASSPORT_ID));
    }

    /**
     * Checks the given string for compliance with the
     * {@link Validator#PASSPORT_SERIES_REGEXP} pattern
     *
     * @param series string for check
     * @return {@code true} if given string
     * is matches to the pattern and {@code false} otherwise
     */
    public static boolean validatePassportSeries(String series) {
        if (!validateString(series)) {
            return false;
        }
        Pattern pattern = Pattern.compile(PASSPORT_SERIES_REGEXP);
        Matcher m = pattern.matcher(series);
        return m.matches();
    }

    /**
     * Checks the given string for compliance with the
     * {@link Validator#NAME_REGEXP} pattern
     *
     * @param name string for check
     * @return {@code true} if given string
     * is matches to the pattern and {@code false} otherwise
     */
    public static boolean validateName(String name) {
        if (!validateString(name)) {
            return false;
        }
        Pattern pattern = Pattern.compile(NAME_REGEXP);
        Matcher m = pattern.matcher(name);
        return m.matches();
    }

    /**
     * Checks the given date if in the range
     *
     * @param birthday date for check
     * @return {@code true} if given date
     * is in the range and {@code false} otherwise
     */
    public static boolean validateBirthdayDate(Date birthday) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        try {
            return validateDate(birthday) &&
                    birthday.after(format.parse(BIRTHDAY_DATE_MIN)) &&
                    birthday.before(format.parse(BIRTHDAY_DATE_MAX));
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Checks the given date if in the range
     *
     * @param date date for check
     * @return {@code true} if given date
     * is in the range and {@code false} otherwise
     */
    public static boolean validateCheckinDate(Date date) {
        return validateDate(date) &&
                date.after(Date.from(LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC))) &&
                date.before(Date.from(LocalDateTime.now().plusYears(1).toInstant(ZoneOffset.UTC)));
    }

    /**
     * Checks the given date for compliance with the
     * {@link Validator#DATE_REGEXP} pattern
     *
     * @param date date for check
     * @return {@code true} if given date
     * is matches to the pattern and {@code false} otherwise
     */
    public static boolean validateDate(Date date) {
        if (date == null) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String dateStr;
        try {
            dateStr = format.format(date);
        } catch (Exception e) {
            return false;
        }
        Pattern pattern = Pattern.compile(DATE_REGEXP);
        Matcher m = pattern.matcher(dateStr);

        return m.matches();

    }

    /**
     * Checks days stay number.
     *
     * @param daysStayNumber a days stay number
     * @return {@code true} if days stay number is valid, and {@code false} otherwise
     */
    public static boolean validateDaysStayNumber(int daysStayNumber) {
        return validateInt(daysStayNumber) && daysStayNumber <= MAX_DAYS_STAY_NUMBER;
    }

    /**
     * Checks seats number.
     *
     * @param seatsNumber a seats number
     * @return {@code true} if seats number is valid, and {@code false} otherwise
     */
    public static boolean validateSeatsNumber(int seatsNumber) {
        return validateInt(seatsNumber) && seatsNumber <= MAX_SEATS_NUMBER;
    }

    /**
     * Checks schedule record parameters.
     *
     * @param scheduleRecord a schedule record
     * @return {@code true} if all data ara valid, and {@code false} otherwise
     */
    public static boolean validateScheduleRecord(ScheduleRecord scheduleRecord) {
        return validateInt(scheduleRecord.getRoomNumber()) &&
                validateDate(scheduleRecord.getCheckInDate()) &&
                validateDate(scheduleRecord.getCheckoutDate());
    }

    /**
     * Checks rental request and schedule record parameters.
     *
     * @param scheduleRecord a schedule record
     * @param rentalRequest  a rental request
     * @return {@code true} if all data ara valid, and {@code false} otherwise
     */
    public static boolean validateRentalRequest(ScheduleRecord scheduleRecord, RentalRequest rentalRequest) {
        return validateScheduleRecord(scheduleRecord) &&
                validateInt(rentalRequest.getClient().getId()) &&
                validateDaysStayNumber(rentalRequest.getDaysStayNumber()) &&
                validateSeatsNumber(rentalRequest.getSeatsNumber()) &&
                validateCheckinDate(rentalRequest.getCheckInDate()) &&
                (rentalRequest.isFullPayment()) ?
                validateInt(rentalRequest.getPayment()) : validateInt(scheduleRecord.getPaymentDuty());
    }
}
