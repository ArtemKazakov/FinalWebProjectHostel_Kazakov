package com.epam.hostel.service;


import com.epam.hostel.bean.entity.User;
import com.epam.hostel.service.exception.ServiceException;

import java.util.Date;

/**
 * Created by ASUS on 19.10.2016.
 */
public interface SiteService {

    User logIn(String login, String password) throws ServiceException;

    void registration(String login, String password, int identificationNumber, String series, String surname,
                      String name, String lastName, Date birthday) throws ServiceException;
}

