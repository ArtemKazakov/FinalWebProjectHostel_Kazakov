package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.exception.DAOException;

/**
 * Created by ASUS on 19.10.2016.
 */
public interface UserDAO {

    void insert(User user) throws DAOException;

    void update(User user) throws DAOException;

    User findByLogin(String login) throws DAOException;

    User findById(int id) throws DAOException;

}
