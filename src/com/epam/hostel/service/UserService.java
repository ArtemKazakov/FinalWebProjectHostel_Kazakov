package com.epam.hostel.service;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.service.exception.ServiceException;

import java.util.Date;

/**
 * Created by ASUS on 09.11.2016.
 */
public interface UserService {
    User getUserById(int id) throws ServiceException;

    void updateUser(int id, String login, String password, int identificationNumber, String series, String surname,
                    String name, String lastName, Date birthday) throws ServiceException;
}
