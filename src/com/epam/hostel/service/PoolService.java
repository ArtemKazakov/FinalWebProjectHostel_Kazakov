package com.epam.hostel.service;

import com.epam.hostel.service.exception.ServiceException;

public interface PoolService {

    void init() throws ServiceException;

    void destroy() throws ServiceException;
}
