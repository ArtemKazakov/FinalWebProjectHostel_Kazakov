package com.epam.hostel.dao;

import com.epam.hostel.dao.exception.DAOException;

public interface PoolDAO {

    void init() throws DAOException;

    void destroy() throws DAOException;
}
