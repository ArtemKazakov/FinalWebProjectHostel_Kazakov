package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.dao.DiscountDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.dao.transaction.TransactionManager;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class MySQLDiscountDAOTest {
    private static final String DAO_CONFIGURATION = "/bean/daobeans.xml";

    private static DiscountDAO dao;
    private static TransactionManager transactionManager = TransactionManagerImpl.getInstance();

    @BeforeClass
    public static void init() {
        DAOFactory.getInstance().inject(DAO_CONFIGURATION);
        dao = DAOFactory.getInstance().getDiscountDAO();
        try {
            DAOFactory.getInstance().getPoolDAO().init();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert() throws Exception {
        try {
            Discount expectedDiscount = new Discount();
            expectedDiscount.setClientId(1);
            expectedDiscount.setValue(20);
            expectedDiscount.setAdministratorId(2);
            transactionManager.doInTransaction(() -> {
                dao.insert(expectedDiscount);

                List<Discount> discounts = dao.findAll();
                Discount actualDiscount = discounts.get(discounts.size() - 1);

                dao.delete(actualDiscount.getId());

                Assert.assertEquals(expectedDiscount.getClientId(), actualDiscount.getClientId());
                Assert.assertEquals(expectedDiscount.getValue(), actualDiscount.getValue());
                Assert.assertEquals(expectedDiscount.getAdministratorId(), actualDiscount.getAdministratorId());

                return Optional.empty();
            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        try {
            Discount discount = new Discount();
            discount.setClientId(1);
            discount.setValue(20);
            discount.setAdministratorId(2);
            transactionManager.doInTransaction(() -> {
                dao.insert(discount);

                List<Discount> discounts = dao.findAll();
                Discount insertedDiscount = discounts.get(discounts.size() - 1);

                insertedDiscount.setValue(30);
                insertedDiscount.setAdministratorId(1);

                dao.update(insertedDiscount);

                insertedDiscount = dao.findById(insertedDiscount.getId());

                Assert.assertEquals(insertedDiscount.getClientId(), 1);
                Assert.assertEquals(insertedDiscount.getValue(), 30);
                Assert.assertEquals(insertedDiscount.getAdministratorId(), 1);

                dao.delete(insertedDiscount.getId());

                return Optional.empty();
            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateValue() throws Exception {
        try {
            Discount discount = new Discount();
            discount.setClientId(1);
            discount.setValue(20);
            discount.setAdministratorId(2);
            transactionManager.doInTransaction(() -> {
                dao.insert(discount);

                List<Discount> discounts = dao.findAll();
                Discount insertedDiscount = discounts.get(discounts.size() - 1);

                insertedDiscount.setValue(30);

                dao.updateValue(insertedDiscount);

                insertedDiscount = dao.findById(insertedDiscount.getId());

                Assert.assertEquals(insertedDiscount.getValue(), 30);

                dao.delete(insertedDiscount.getId());

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
                List<Discount> discounts = dao.findAll();
                Discount discount = discounts.get(discounts.size() - 1);
                int expectedCount = discounts.size() - 1;
                dao.delete(discount.getId());
                discounts = dao.findAll();
                int actualCount = discounts.size();
                dao.insert(discount);
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
            Discount discount = transactionManager.doInTransaction(() -> dao.findById(1));
            assertNotNull(discount);
            assertTrue(discount.getId() == 1);
        } catch(DAOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByClientId() throws Exception {
        try {
            transactionManager.doInTransaction(() -> {
                Discount discount = new Discount();
                discount.setClientId(1);
                discount.setValue(2);
                discount.setAdministratorId(1);
                dao.insert(discount);
                List<Discount> discounts = dao.findByClientId(1);
                assertNotNull(discounts);
                assertTrue(discounts.size() > 0);

                dao.delete(discounts.get(discounts.size() - 1).getId());

                return Optional.empty();
            });
        } catch(DAOException e){
            e.printStackTrace();
        }

    }

    @Test
    public void testFindUnusedByClientId() throws Exception {
        try {
            transactionManager.doInTransaction(() -> {
                Discount discount = new Discount();
                discount.setClientId(1);
                discount.setValue(2);
                discount.setAdministratorId(1);
                dao.insert(discount);
                List<Discount> discounts = dao.findUnusedByClientId(1);
                assertNotNull(discounts);
                assertTrue(discounts.size() > 0);

                dao.delete(discounts.get(discounts.size() - 1).getId());

                return Optional.empty();
            });
        } catch(DAOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByAdministratorId() throws Exception {
        try {
            transactionManager.doInTransaction(() -> {
                Discount discount = new Discount();
                discount.setClientId(1);
                discount.setValue(2);
                discount.setAdministratorId(1);
                dao.insert(discount);
                List<Discount> discounts = dao.findByAdministratorId(1);
                assertNotNull(discounts);
                assertTrue(discounts.size() > 0);

                dao.delete(discounts.get(discounts.size() - 1).getId());

                return Optional.empty();
            });
        } catch(DAOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAll() throws Exception {
        try {
            List<Discount> list = transactionManager.doInTransaction(dao :: findAll);
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