package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Created by ASUS on 16.11.2016.
 */
public interface RequestDAO {

    int insert(RentalRequest rentalRequest) throws DAOException;

    void update(RentalRequest rentalRequest) throws DAOException;

    void delete(int id) throws DAOException;

    RentalRequest findById(int id) throws DAOException;

    List<RentalRequest> findByClientId(int id) throws DAOException;

    List<RentalRequest> findByAdministratorId(int id) throws DAOException;

    List<RentalRequest> findAll() throws DAOException;
}
