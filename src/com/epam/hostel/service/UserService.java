package com.epam.hostel.service;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.service.exception.ServiceException;

/**
 * Created by ASUS on 09.11.2016.
 */
public interface UserService {
    User getUserById(int id) throws ServiceException;
}
