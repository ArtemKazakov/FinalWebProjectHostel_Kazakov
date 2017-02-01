package com.epam.hostel.service.util;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class PasswordHelperTest {

    @Test
    public void passwordManagerTest(){
        PasswordHelper passwordHelper = PasswordHelper.getInstance();
        byte[] wrongPassword = "password".getBytes(StandardCharsets.UTF_8);
        byte[] hashedPassword = passwordHelper.encryptPassword("anotherpassword".getBytes(StandardCharsets.UTF_8));
        byte[] password = "anotherpassword".getBytes(StandardCharsets.UTF_8);
        assertTrue(passwordHelper.match(password, hashedPassword));
        assertFalse(passwordHelper.match(wrongPassword, hashedPassword));
    }
}