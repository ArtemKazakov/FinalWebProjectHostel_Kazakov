package com.epam.hostel.service.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void testValidateInt() throws Exception {
        assertTrue(Validator.validateInt(2));
        assertTrue(Validator.validateInt(24));
        assertFalse(Validator.validateInt(-5));
    }

    @Test
    public void testValidateString() throws Exception {
        assertTrue(Validator.validateString("test"));
        assertFalse(Validator.validateString(""));
        assertFalse(Validator.validateString(null));
    }

    @Test
    public void testValidateDiscountValue() throws Exception {
        assertTrue(Validator.validateDiscountValue(42));
        assertFalse(Validator.validateDiscountValue(33333));
        assertFalse(Validator.validateDiscountValue(-2));
    }

    @Test
    public void testValidateRoomNumber() throws Exception {
        assertTrue(Validator.validateRoomNumber(323));
        assertFalse(Validator.validateRoomNumber(33333));
        assertFalse(Validator.validateRoomNumber(-2));
    }

    @Test
    public void testValidatePerdayCost() throws Exception {
        assertTrue(Validator.validatePerdayCost(32));
        assertFalse(Validator.validatePerdayCost(32523));
        assertFalse(Validator.validatePerdayCost(-2));
    }

    @Test
    public void testValidateLogin() throws Exception {
        assertTrue(Validator.validateLogin("testlogin"));
        assertTrue(Validator.validateLogin("nottoooooooooooooooooooooooooooooooolong"));
        assertFalse(Validator.validateLogin("русскиебуквы"));
        assertFalse(Validator.validateLogin("smal"));
        assertFalse(Validator.validateLogin("toooooooooooooooooooooooooooooooooooolong"));
    }

    @Test
    public void testValidatePassword() throws Exception {
        assertTrue(Validator.validatePassword("Aa123456".getBytes()));
        assertTrue(Validator.validatePassword("123aAa342".getBytes()));
        assertFalse(Validator.validatePassword("a123454543".getBytes()));
        assertFalse(Validator.validatePassword("231314124124".getBytes()));
        assertFalse(Validator.validatePassword("Aa123".getBytes()));
    }

    @Test
    public void testValidatePassportIdNumber() throws Exception {
        assertTrue(Validator.validatePassportIdNumber(1234567));
        assertFalse(Validator.validatePassportIdNumber(12345678));
        assertFalse(Validator.validatePassportIdNumber(123456));
    }

    @Test
    public void testValidatePassportSeries() throws Exception {
        assertTrue(Validator.validatePassportSeries("GF"));
        assertFalse(Validator.validatePassportSeries("F"));
        assertFalse(Validator.validatePassportSeries("FDF"));
    }

    @Test
    public void testValidateName() throws Exception {
        assertTrue(Validator.validateName("ya"));
        assertTrue(Validator.validateName("Fgdfg-j"));
        assertFalse(Validator.validateName("fdsffsd43"));
        assertFalse(Validator.validateName("f"));
        assertFalse(Validator.validateName(""));
    }

    @Test
    public void testValidateDaysStayNumber() throws Exception {
        assertTrue(Validator.validateDaysStayNumber(30));
        assertFalse(Validator.validateDaysStayNumber(-2));
        assertFalse(Validator.validateDaysStayNumber(32));
    }

    @Test
    public void testValidateSeatsNumber() throws Exception {
        assertTrue(Validator.validateSeatsNumber(5));
        assertFalse(Validator.validatePassportIdNumber(6));
        assertFalse(Validator.validatePassportIdNumber(0));
    }

    @Test
    public void testValidateBirthdayDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        assertTrue(Validator.validateBirthdayDate(format.parse("1990-10-10")));
        assertTrue(Validator.validateBirthdayDate(format.parse("1998-12-31")));
        assertTrue(Validator.validateBirthdayDate(format.parse("1920-01-01")));
        assertFalse(Validator.validateBirthdayDate(format.parse("1919-12-31")));
    }

    @Test
    public void testValidateCheckInDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        assertTrue(Validator.validateCheckinDate(format.parse("2017-06-06")));
    }
}