package com.epam.hostel.bean.entity;

import java.util.Arrays;

/**
 * Created by ASUS on 27.10.2016.
 */
public class User {

    private int id;
    private String login;
    private byte[] password;
    private boolean banned;
    private int visitsNumber;
    private Passport passport;
    private boolean admin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public int getVisitsNumber() {
        return visitsNumber;
    }

    public void setVisitsNumber(int visitsNumber) {
        this.visitsNumber = visitsNumber;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (banned != user.banned) return false;
        if (visitsNumber != user.visitsNumber) return false;
        if (admin != user.admin) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (!Arrays.equals(password, user.password)) return false;
        return passport != null ? passport.equals(user.passport) : user.passport == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(password);
        result = 31 * result + (banned ? 1 : 0);
        result = 31 * result + visitsNumber;
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password=" + "hidden" +
                ", banned=" + banned +
                ", visitsNumber=" + visitsNumber +
                ", passport=" + passport +
                ", admin=" + admin +
                '}';
    }
}
