package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.UserDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.dao.transaction.TransactionManager;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;
import org.apache.xerces.impl.dv.util.Base64;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class MySQLUserDAOTest {
    private static final String DAO_CONFIGURATION = "/bean/daobeans.xml";

    private static UserDAO dao;
    private static TransactionManager transactionManager = TransactionManagerImpl.getInstance();

    @BeforeClass
    public static void init() {
        DAOFactory.getInstance().inject(DAO_CONFIGURATION);
        dao = DAOFactory.getInstance().getUserDAO();
        try {
            DAOFactory.getInstance().getPoolDAO().init();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert() throws Exception {
        try {
            Passport passport = new Passport();
            passport.setIdentificationNumber(1234567);
            passport.setSeries("BM");
            passport.setSurname("Surname");
            passport.setName("Name");
            passport.setPatronymic("Patronymic");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2010);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            passport.setBirthday(cal.getTime());
            User expectedUser = new User();
            expectedUser.setLogin("testlogin");
            expectedUser.setPassword("testpassword".getBytes());
            transactionManager.doInTransaction(() -> {
                PassportDAO passportDAO = DAOFactory.getInstance().getPassportDAO();

                int id = passportDAO.insert(passport);
                passport.setId(id);
                expectedUser.setPassport(passport);

                dao.insert(expectedUser);

                List<User> users = dao.findAll(0, 1000);
                User actualUser = users.get(users.size() - 1);

                dao.delete(actualUser.getId());
                passportDAO.delete(id);

                Assert.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
                Assert.assertEquals(Base64.encode(expectedUser.getPassword()), Base64.encode(actualUser.getPassword()));

                return Optional.empty();
            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        try {
            Passport passport = new Passport();
            passport.setIdentificationNumber(1234567);
            passport.setSeries("BM");
            passport.setSurname("Surname");
            passport.setName("Name");
            passport.setPatronymic("Patronymic");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2010);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            passport.setBirthday(cal.getTime());
            User user = new User();
            user.setLogin("testlogin");
            user.setPassword("testpassword".getBytes());
            transactionManager.doInTransaction(() -> {
                PassportDAO passportDAO = DAOFactory.getInstance().getPassportDAO();

                int id = passportDAO.insert(passport);
                passport.setId(id);
                user.setPassport(passport);

                dao.insert(user);

                List<User> users = dao.findAll(0, 1000);
                User insertedUser = users.get(users.size() - 1);

                insertedUser.setLogin("testlogin2");
                insertedUser.setPassword("testpassword2".getBytes());

                dao.update(insertedUser);

                insertedUser = dao.findByIdAndRole(insertedUser.getId(), false);

                Assert.assertEquals(insertedUser.getLogin(), "testlogin2");
                Assert.assertEquals(Base64.encode(insertedUser.getPassword()), Base64.encode("testpassword2".getBytes()));

                dao.delete(insertedUser.getId());
                passportDAO.delete(id);

                return Optional.empty();
            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateBanned() throws Exception {
        try {
            Passport passport = new Passport();
            passport.setIdentificationNumber(1234567);
            passport.setSeries("BM");
            passport.setSurname("Surname");
            passport.setName("Name");
            passport.setPatronymic("Patronymic");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2010);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            passport.setBirthday(cal.getTime());
            User user = new User();
            user.setLogin("testlogin");
            user.setPassword("testpassword".getBytes());
            transactionManager.doInTransaction(() -> {
                PassportDAO passportDAO = DAOFactory.getInstance().getPassportDAO();

                int id = passportDAO.insert(passport);
                passport.setId(id);
                user.setPassport(passport);

                dao.insert(user);

                List<User> users = dao.findAll(0, 1000);
                User insertedUser = users.get(users.size() - 1);

                insertedUser.setBanned(true);

                dao.updateBanned(insertedUser);

                insertedUser = dao.findByIdAndRole(insertedUser.getId(), false);

                Assert.assertEquals(insertedUser.isBanned(), true);

                dao.delete(insertedUser.getId());
                passportDAO.delete(id);

                return Optional.empty();
            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByLogin() throws Exception {
        try {
            User user = transactionManager.doInTransaction(() -> dao.findByLogin("ivanov_ivan"));
            assertNotNull(user);
            assertTrue(user.getId() == 1);
        } catch(DAOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByIdAndRole() throws Exception {
        try {
            User user = transactionManager.doInTransaction(() -> dao.findByIdAndRole(1, false));
            assertNotNull(user);
            assertTrue(user.getId() == 1);
        } catch(DAOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAll() throws Exception {
        try {
            List<User> list = transactionManager.doInTransaction(() -> dao.findAll(0, 1000));
            assertNotNull(list);
            assertTrue(list.size() > 0);
        } catch(DAOException e){
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void destroy() {
        try {
            DAOFactory.getInstance().getPoolDAO().destroy();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}