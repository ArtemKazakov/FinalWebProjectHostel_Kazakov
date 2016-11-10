package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;

/**
 * Created by ASUS on 09.11.2016.
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(int id) throws ServiceException {
        if(!Validator.validateInt(id)){
            throw new ServiceException("Wrong id for getting user");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        UserDAO userDAO = factory.getUserDAO();
        PassportDAO passportDAO = factory.getPassportDAO();

        try{
            User user = userDAO.findById(id);
            if(user != null){
                Passport passport = passportDAO.findById(user.getPassport().getId());
                user.setPassport(passport);
            }
            return user;
        } catch (DAOException e){
            throw new ServiceException("Service layer: cannot get user by id", e);
        }
    }
}
