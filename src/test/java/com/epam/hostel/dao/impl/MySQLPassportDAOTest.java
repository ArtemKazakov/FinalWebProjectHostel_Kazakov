package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Passport;
import com.epam.hostel.dao.PassportDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.dao.transaction.TransactionManager;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


public class MySQLPassportDAOTest {
    private static final String DAO_CONFIGURATION = "/bean/daobeans.xml";

    private static PassportDAO dao;
    private static TransactionManager transactionManager = TransactionManagerImpl.getInstance();

    @BeforeClass
    public static void init() {
        DAOFactory.getInstance().inject(DAO_CONFIGURATION);
        dao = DAOFactory.getInstance().getPassportDAO();
        try {
            DAOFactory.getInstance().getPoolDAO().init();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert() throws Exception {
        try {
            Passport expectedPassport = new Passport();
            expectedPassport.setIdentificationNumber(1234567);
            expectedPassport.setSeries("BM");
            expectedPassport.setSurname("Surame");
            expectedPassport.setName("Name");
            expectedPassport.setPatronymic("Patronymic");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2010);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            expectedPassport.setBirthday(cal.getTime());
            transactionManager.doInTransaction(() -> {
                int id = dao.insert(expectedPassport);

                Passport actualPassport = dao.findById(id);

                dao.delete(id);

                Assert.assertEquals(expectedPassport.getIdentificationNumber(), actualPassport.getIdentificationNumber());
                Assert.assertEquals(expectedPassport.getSeries(), actualPassport.getSeries());
                Assert.assertEquals(expectedPassport.getSurname(), actualPassport.getSurname());
                Assert.assertEquals(expectedPassport.getName(), actualPassport.getName());
                Assert.assertEquals(expectedPassport.getPatronymic(), actualPassport.getPatronymic());

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
            transactionManager.doInTransaction(() -> {
                int id = dao.insert(passport);

                Passport insertedPassport = dao.findById(id);

                insertedPassport.setName("ChangeName");
                insertedPassport.setPatronymic("ChangePatronymic");

                dao.update(insertedPassport);

                insertedPassport = dao.findById(id);

                Assert.assertEquals(insertedPassport.getName(), "ChangeName");
                Assert.assertEquals(insertedPassport.getPatronymic(), "ChangePatronymic");

                dao.delete(id);

                return Optional.empty();
            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindById() throws Exception {
        try {
            Passport passport = transactionManager.doInTransaction(() -> dao.findById(1));
            assertNotNull(passport);
            assertTrue(passport.getId() == 1);
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