package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.dao.exception.DAOException;

/**
 * Created by ASUS on 29.10.2016.
 */
public interface PassportDAO {

    int insert(Passport passport) throws DAOException;

    void update(Passport passport) throws DAOException;

    Passport findById(int id) throws DAOException;


}
