package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.Request;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Created by ASUS on 16.11.2016.
 */
public interface RequestDAO {

    void insert(Request request) throws DAOException;

    void update(Request request) throws DAOException;

    void delete(int id) throws DAOException;

    List<Request> findByClientId(int id) throws DAOException;

    List<Request> findByAdministratorId(int id) throws DAOException;

    List<Request> selectAll() throws DAOException;
}
