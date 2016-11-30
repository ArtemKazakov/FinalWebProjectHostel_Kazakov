package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Created by ASUS on 15.11.2016.
 */
public interface DiscountDAO {

    void insert(Discount discount) throws DAOException;

    void update(Discount discount) throws DAOException;

    void delete(int id) throws DAOException;

    List<Discount> findByClientId(int id) throws DAOException;

    List<Discount> findByAdministratorId(int id) throws DAOException;

    List<Discount> findAll() throws DAOException;

}
