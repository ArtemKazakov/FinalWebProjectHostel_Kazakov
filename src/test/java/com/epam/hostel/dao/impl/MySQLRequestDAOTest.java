package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.RequestDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.dao.transaction.TransactionManager;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


public class MySQLRequestDAOTest {
    private static final String DAO_CONFIGURATION = "/bean/daobeans.xml";

    private static RequestDAO dao;
    private static TransactionManager transactionManager = TransactionManagerImpl.getInstance();

    @BeforeClass
    public static void init() {
        DAOFactory.getInstance().inject(DAO_CONFIGURATION);
        dao = DAOFactory.getInstance().getRequestDAO();
        try {
            DAOFactory.getInstance().getPoolDAO().init();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testInsert() throws Exception {
        try {
            RentalRequest expectedRentalRequest = new RentalRequest();
            User client = new User();
            client.setId(1);
            expectedRentalRequest.setClient(client);
            expectedRentalRequest.setSeatsNumber(2);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2010);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            expectedRentalRequest.setCheckInDate(cal.getTime());
            expectedRentalRequest.setDaysStayNumber(2);
            expectedRentalRequest.setFullPayment(true);
            expectedRentalRequest.setPayment(100);
            transactionManager.doInTransaction(() -> {
                int id = dao.insert(expectedRentalRequest);

                RentalRequest actualRentalRequest = dao.findById(id);

                dao.delete(id);

                Assert.assertEquals(expectedRentalRequest.getClient().getId(), actualRentalRequest.getClient().getId());
                Assert.assertEquals(expectedRentalRequest.getSeatsNumber(), actualRentalRequest.getSeatsNumber());
                Assert.assertEquals(expectedRentalRequest.getDaysStayNumber(), actualRentalRequest.getDaysStayNumber());
                Assert.assertEquals(expectedRentalRequest.isFullPayment(), actualRentalRequest.isFullPayment());
                Assert.assertEquals(expectedRentalRequest.getPayment(), actualRentalRequest.getPayment());

                return Optional.empty();
            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        try {
            RentalRequest rentalRequest = new RentalRequest();
            User user = new User();
            user.setId(1);
            rentalRequest.setClient(user);
            rentalRequest.setSeatsNumber(2);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2010);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            rentalRequest.setCheckInDate(cal.getTime());
            rentalRequest.setDaysStayNumber(2);
            rentalRequest.setFullPayment(true);
            rentalRequest.setPayment(100);

            transactionManager.doInTransaction(() -> {
                int id = dao.insert(rentalRequest);

                RentalRequest insertedRentalRequest = dao.findById(id);

                insertedRentalRequest.setPayment(20);
                insertedRentalRequest.setDaysStayNumber(5);
                insertedRentalRequest.setSeatsNumber(4);
                insertedRentalRequest.setAdministrator(user);
                insertedRentalRequest.setAccepted(true);

                dao.update(insertedRentalRequest);

                insertedRentalRequest = dao.findById(id);

                Assert.assertEquals(insertedRentalRequest.getPayment(), 20);
                Assert.assertEquals(insertedRentalRequest.getDaysStayNumber(), 5);
                Assert.assertEquals(insertedRentalRequest.getSeatsNumber(), 4);

                dao.delete(id);

                return Optional.empty();
            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete() throws Exception {
        try {
            transactionManager.doInTransaction(() -> {
                List<RentalRequest> rentalRequests = dao.findAll();
                RentalRequest rentalRequest = rentalRequests.get(rentalRequests.size() - 1);
                int expectedCount = rentalRequests.size() - 1;
                dao.delete(rentalRequest.getId());
                rentalRequests = dao.findAll();
                int actualCount = rentalRequests.size();
                dao.insert(rentalRequest);
                Assert.assertEquals(expectedCount, actualCount);

                return Optional.empty();
            });
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindById() throws Exception {
        try {
            RentalRequest rentalRequest = transactionManager.doInTransaction(() -> dao.findById(1));
            assertNotNull(rentalRequest);
            assertTrue(rentalRequest.getId() == 1);
        } catch(DAOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByClientId() throws Exception {
        try {
            transactionManager.doInTransaction(() -> {
                RentalRequest rentalRequest = new RentalRequest();
                User client = new User();
                client.setId(1);
                rentalRequest.setClient(client);
                rentalRequest.setSeatsNumber(2);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, 2010);
                cal.set(Calendar.MONTH, Calendar.JANUARY);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                rentalRequest.setCheckInDate(cal.getTime());
                rentalRequest.setDaysStayNumber(2);
                rentalRequest.setFullPayment(true);
                rentalRequest.setPayment(100);
                dao.insert(rentalRequest);
                List<RentalRequest> rentalRequests = dao.findByClientId(1);
                assertNotNull(rentalRequest);
                assertTrue(rentalRequests.size() > 0);

                dao.delete(rentalRequests.get(rentalRequests.size() - 1).getId());

                return Optional.empty();
            });
        } catch(DAOException e){
            e.printStackTrace();
        }
    }


    @Test
    public void testFindAll() throws Exception {
        try {
            List<RentalRequest> list = transactionManager.doInTransaction(dao :: findAll);
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