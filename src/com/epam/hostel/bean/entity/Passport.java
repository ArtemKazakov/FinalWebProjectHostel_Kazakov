package com.epam.hostel.bean.entity;

import java.util.Date;

/**
 * Created by ASUS on 27.10.2016.
 */
public class Passport {

    private int id;
    private int identificationNumber;
    private String series;
    private String surname;
    private String name;
    private String patronymic;
    private Date birthday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passport passport = (Passport) o;

        if (id != passport.id) return false;
        if (identificationNumber != passport.identificationNumber) return false;
        if (series != null ? !series.equals(passport.series) : passport.series != null) return false;
        if (surname != null ? !surname.equals(passport.surname) : passport.surname != null) return false;
        if (name != null ? !name.equals(passport.name) : passport.name != null) return false;
        if (patronymic != null ? !patronymic.equals(passport.patronymic) : passport.patronymic != null) return false;
        if (birthday != null ? !birthday.equals(passport.birthday) : passport.birthday != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + identificationNumber;
        result = 31 * result + (series != null ? series.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", identificationNumber=" + identificationNumber +
                ", series='" + series + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
