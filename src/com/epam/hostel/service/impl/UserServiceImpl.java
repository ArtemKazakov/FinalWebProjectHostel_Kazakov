package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceWrongLoginException;
import com.epam.hostel.service.exception.ServiceWrongPasswordException;

import java.util.Date;
import java.util.List;

/**
 * Created by ASUS on 09.11.2016.
 */
public class UserServiceImpl implements UserService {

    private static final int SERIES_MAX_LENGTH = 2;
    private static final int SURNAME_MAX_LENGTH = 40;
    private static final int NAME_MAX_LENGTH = 40;
    private static final int PATRONYMIC_MAX_LENGTH = 40;

    @Override
    public User getUserByIdAndRole(int id, boolean isAdmin) throws ServiceException {
        if(!Validator.validateInt(id)){
            throw new ServiceException("Wrong id for getting user");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        UserDAO userDAO = factory.getUserDAO();
        PassportDAO passportDAO = factory.getPassportDAO();

        try{
            User user = userDAO.findByIdAndRole(id, isAdmin);
            if(user != null){
                Passport passport = passportDAO.findById(user.getPassport().getId());
                user.setPassport(passport);
            }
            return user;
        } catch (DAOException e){
            throw new ServiceException("Service layer: cannot get user by id", e);
        }
    }

    @Override
    public void updateUser(int id, String login, String password, int identificationNumber, String series,
                           String surname, String name, String patronymic, Date birthday) throws ServiceException {
        if(!Validator.validateLogin(login)){
            throw new ServiceWrongLoginException("Wrong login");
        }
        if(!Validator.validatePassword(password)){
            throw new ServiceWrongPasswordException("Wrong password");
        }
        if(!Validator.validateInt(id) || !Validator.validateInt(identificationNumber) ||
                !Validator.validateString(series, SERIES_MAX_LENGTH) || !Validator.validateString(surname, SURNAME_MAX_LENGTH) ||
                !Validator.validateString(name, NAME_MAX_LENGTH)
                || !Validator.validateString(patronymic, PATRONYMIC_MAX_LENGTH) ||  birthday == null){
            throw new ServiceException("Wrong parameters for registration");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        UserDAO userDAO = factory.getUserDAO();
        PassportDAO passportDAO = factory.getPassportDAO();

        try{
            User userWithThisId = userDAO.findByIdAndRole(id, false);

            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setId(id);

            userDAO.update(user);

            Passport passport = new Passport();
            passport.setId(userWithThisId.getPassport().getId());
            passport.setIdentificationNumber(identificationNumber);
            passport.setSeries(series);
            passport.setSurname(surname);
            passport.setName(name);
            passport.setPatronymic(patronymic);
            passport.setBirthday(birthday);

            passportDAO.update(passport);

        } catch (DAOException e){
            throw new ServiceException("Service layer: cannot make a registration", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        UserDAO userDao = factory.getUserDAO();
        PassportDAO passportDAO = factory.getPassportDAO();
        List<User> users = null;

        try{
            users = userDao.findAll();
            for (User user : users){
                Passport passport = passportDAO.findById(user.getPassport().getId());
                user.setPassport(passport);
            }
            return users;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all rooms", e);
        }
    }
}
