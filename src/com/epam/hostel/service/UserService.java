package com.epam.hostel.service;

import com.epam.hostel.bean.entity.User;
import com.epam.hostel.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

/**
 * Created by ASUS on 09.11.2016.
 */
public interface UserService {
    User getUserByIdAndRole(int id, boolean isAdmin) throws ServiceException;

    void updateUser(int id, String login, String password, int identificationNumber, String series, String surname,
                    String name, String patronymic, Date birthday) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;
}
