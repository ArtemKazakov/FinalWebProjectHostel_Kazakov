package com.epam.hostel.service.impl;


import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.dao.transaction.TransactionManager;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;
import com.epam.hostel.service.SiteService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceWrongLoginException;
import com.epam.hostel.service.exception.ServiceWrongPasswordException;
import com.epam.hostel.service.util.PasswordHelper;
import com.epam.hostel.service.util.Validator;
import org.apache.log4j.Logger;

import java.util.Optional;

/**
 * Provides a business-logic with the {@link User} entity and the {@link Passport} entity.
 */
public class SiteServiceImpl extends Service implements SiteService {
    private static final Logger logger = Logger.getLogger(SiteServiceImpl.class);
    private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    private final PassportDAO passportDAO = DAOFactory.getInstance().getPassportDAO();
    private static TransactionManager transactionManager = TransactionManagerImpl.getInstance();

    /**
     * Checks if a user with this login and password can log in to the system.
     *
     * @param login    a login of the user
     * @param password a password of the user
     * @return an user object if log in success
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public User logIn(String login, byte[] password) throws ServiceException {
        if (!Validator.validateLogin(login)) {
            throw new ServiceWrongLoginException("Wrong login");
        }
        if (!Validator.validatePassword(password)) {
            throw new ServiceWrongPasswordException("Wrong password");
        }

        try {
            User user = transactionManager.doInTransaction(() -> userDAO.findByLogin(login));
            if (user == null) {
                throw new ServiceWrongLoginException("Wrong login");
            }
            if (!PasswordHelper.getInstance().match(password, user.getPassword())) {
                throw new ServiceWrongPasswordException("Wrong password");
            }
            return user;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot make a login operation", e);
        }
    }

    /**
     * Registers a new user to the system.
     *
     * @param user     an user account
     * @param passport an user passport
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void registration(User user, Passport passport) throws ServiceException {
        if (!Validator.validateLogin(user.getLogin())) {
            throw new ServiceWrongLoginException("Wrong login");
        }
        if (!Validator.validatePassword(user.getPassword())) {
            throw new ServiceWrongPasswordException("Wrong password");
        }
        if (!Validator.validatePassportIdNumber(passport.getIdentificationNumber()) ||
                !Validator.validatePassportSeries(passport.getSeries()) ||
                !Validator.validateName(passport.getSurname()) ||
                !Validator.validateName(passport.getName()) ||
                !Validator.validateName(passport.getPatronymic()) ||
                !Validator.validateBirthdayDate(passport.getBirthday())) {
            throw new ServiceException("Wrong parameters for registration");
        }

        byte[] encryptPassword = PasswordHelper.getInstance().encryptPassword(user.getPassword());

        try {
            User userWithThisLogin = transactionManager.doInTransaction(() -> userDAO.findByLogin(user.getLogin()));
            if (userWithThisLogin != null) {
                throw new ServiceWrongLoginException("Wrong login");
            }
            transactionManager.doInTransaction(() -> {
                int passportId = passportDAO.insert(passport);
                passport.setId(passportId);

                user.setPassword(encryptPassword);
                user.setPassport(passport);

                userDAO.insert(user);
                return Optional.empty();
            });
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot make a registration", e);
        } finally {
            PasswordHelper.dispose(encryptPassword);
        }
    }

}
