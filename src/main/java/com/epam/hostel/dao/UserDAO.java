package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Created by ASUS on 19.10.2016.
 */
public interface UserDAO {

    void insert(User user) throws DAOException;

    void update(User user) throws DAOException;

    void updateBanned(User user) throws DAOException;

    void delete(int id) throws DAOException;

    User findByLogin(String login) throws DAOException;

    User findByIdAndRole(int id, boolean isAdmin) throws DAOException;

    List<User> findAll() throws DAOException;

}
