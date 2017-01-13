package com.epam.hostel.service.impl;


import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.SiteService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceWrongLoginException;
import com.epam.hostel.service.exception.ServiceWrongPasswordException;
import com.epam.hostel.service.util.Validator;

import java.util.Date;

/**
 * Created by ASUS on 19.10.2016.
 */
public class SiteServiceImpl implements SiteService {

    private static final int SERIES_MAX_LENGTH = 2;
    private static final int SURNAME_MAX_LENGTH = 40;
    private static final int NAME_MAX_LENGTH = 40;
    private static final int PATRONYMIC_MAX_LENGTH = 40;


    @Override
    public User logIn(String login, String password) throws ServiceException {
        if(!Validator.validateLogin(login)){
            throw new ServiceWrongLoginException("Wrong login");
        }
        if(!Validator.validatePassword(password)){
            throw new ServiceWrongPasswordException("Wrong password");
        }


        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        UserDAO dao = factory.getUserDAO();

        try {
            User user = dao.findByLogin(login);
            if(user == null){
                throw new ServiceWrongLoginException("Wrong login");
            }
            if(!user.getPassword().equals(password)){
                throw new ServiceWrongPasswordException("Wrong password");
            }
            return user;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot make a login operation", e);
        }
    }

    @Override
    public void registration(String login, String password, int identificationNumber, String series, String surname,
                             String name, String patronymic, Date birthday) throws ServiceException{
        if(!Validator.validateLogin(login)){
            throw new ServiceWrongLoginException("Wrong login");
        }
        if(!Validator.validatePassword(password)){
            throw new ServiceWrongPasswordException("Wrong password");
        }
        if(!Validator.validatePassportIdNumber(identificationNumber) ||
                !Validator.validatePassportSeries(series) || !Validator.validateName(surname) ||
                !Validator.validateName(name)
                || !Validator.validateName(patronymic) ||  birthday == null){
            throw new ServiceException("Wrong parameters for registration");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        UserDAO userDAO = factory.getUserDAO();
        PassportDAO passportDAO = factory.getPassportDAO();

        try{
            User userWithThisLogin = userDAO.findByLogin(login);
            if(userWithThisLogin != null){
                throw new ServiceWrongLoginException("Wrong login");
            }

            Passport passport = new Passport();
            passport.setIdentificationNumber(identificationNumber);
            passport.setSeries(series);
            passport.setSurname(surname);
            passport.setName(name);
            passport.setPatronymic(patronymic);
            passport.setBirthday(birthday);

            int passportId = passportDAO.insert(passport);
            passport.setId(passportId);

            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setPassport(passport);

            userDAO.insert(user);

        } catch (DAOException e){
            throw new ServiceException("Service layer: cannot make a registration", e);
        }
    }


}
