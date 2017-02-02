package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.dao.transaction.TransactionManager;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;
import com.epam.hostel.service.UserService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceWrongLoginException;
import com.epam.hostel.service.exception.ServiceWrongPasswordException;
import com.epam.hostel.service.util.PasswordHelper;
import com.epam.hostel.service.util.Validator;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Provides a business-logic with the {@link User} entity and relate with it.
 */
public class UserServiceImpl extends Service implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    private final PassportDAO passportDAO = DAOFactory.getInstance().getPassportDAO();
    private static TransactionManager transactionManager = TransactionManagerImpl.getInstance();

    /**
     * Return a user from a data source by id and role
     *
     * @param id      an id of the user
     * @param isAdmin a role of the user
     * @return a user
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public User getUserByIdAndRole(int id, boolean isAdmin) throws ServiceException {
        if (!Validator.validateInt(id)) {
            throw new ServiceException("Wrong id for getting user");
        }

        try {
            User user = transactionManager.doInTransaction(() -> userDAO.findByIdAndRole(id, isAdmin));
            if (user != null) {
                Passport passport = transactionManager.doInTransaction(() -> passportDAO.findById(user.getPassport().getId()));
                user.setPassport(passport);
            }
            return user;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot get user by id", e);
        }
    }

    /**
     * Updates a user account and passport in a data source
     *
     * @param user     an user account
     * @param passport an user passport
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void updateUser(User user, Passport passport) throws ServiceException {
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
            throw new ServiceException("Wrong parameters for updating user");
        }

        byte[] encryptPassword = PasswordHelper.getInstance().encryptPassword(user.getPassword());

        try {
            User userWithThisId = transactionManager.doInTransaction(() -> userDAO.findByIdAndRole(user.getId(), false));
            User userWithThisLogin = transactionManager.doInTransaction(() -> userDAO.findByLogin(user.getLogin()));

            if (userWithThisLogin != null && userWithThisLogin.getId() != user.getId()) {
                throw new ServiceWrongLoginException("Wrong login");
            }

            user.setPassword(encryptPassword);

            transactionManager.doInTransaction(() -> {
                userDAO.update(user);

                passport.setId(userWithThisId.getPassport().getId());

                passportDAO.update(passport);
                return Optional.empty();
            });
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot make a registration", e);
        } finally {
            PasswordHelper.dispose(encryptPassword);
        }
    }

    /**
     * Return all users from a data source
     *
     * @param start  the number from which accounts will be returned
     * @param amount of users
     * @return a {@link List} of users
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public List<User> getAllUsers(int start, int amount) throws ServiceException {
        try {
            return transactionManager.doInTransaction(() -> {
                List<User> users = userDAO.findAll(start, amount);
                for (User user : users) {
                    Passport passport = passportDAO.findById(user.getPassport().getId());
                    user.setPassport(passport);
                }
                return users;
            });
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot get all users", e);
        }
    }

    /**
     * Updates a user ban status in a data source
     *
     * @param id     an id of the user
     * @param banned a status of ban
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void banUser(int id, boolean banned) throws ServiceException {
        if (!Validator.validateInt(id)) {
            throw new ServiceException("Wrong parameters for ban user");
        }

        User user = new User();
        user.setId(id);
        user.setBanned(banned);

        this.service("Service layer: cannot ban user",
                () -> {
                    userDAO.updateBanned(user);
                    return Optional.empty();
                }
        );
    }

    /**
     * Deletes a user from a data source by id
     *
     * @param id an id of user for deleting
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void deleteUser(int id) throws ServiceException {
        if (!Validator.validateInt(id)) {
            throw new ServiceException("Wrong id for deleting user");
        }

        this.service("Service layer: cannot delete user",
                () -> {
                    User user = userDAO.findByIdAndRole(id, false);
                    userDAO.delete(id);
                    passportDAO.delete(user.getPassport().getId());
                    return Optional.empty();
                }
        );
    }

    /**
     * Returns number of users in data source.
     *
     * @return amount of users
     * @throws ServiceException if error occurred with data source
     */
    @Override
    public int getUsersCount() throws ServiceException {
        return this.service("Service layer: cannot get count of users",
                userDAO::selectUserCount
        );
    }

}
