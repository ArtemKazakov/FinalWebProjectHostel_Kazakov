package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.dao.DiscountDAO;
import com.epam.hostel.dao.PoolDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by ASUS on 15.11.2016.
 */
public class MySQLDiscountDAOTest {

    @Test
    public void testInsert() throws Exception {
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        DiscountDAO discountDAO = null;
        try {
            daoFactory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
            poolDAO = daoFactory.getPoolDAO();
            discountDAO = daoFactory.getDiscountDAO();

            poolDAO.init();

            Discount expectedDiscount = new Discount();
            expectedDiscount.setClientId(1);
            expectedDiscount.setValue(20);
            expectedDiscount.setAdministratorId(2);
            discountDAO.insert(expectedDiscount);

            List<Discount> discounts = discountDAO.findAll();
            Discount actualDiscount = discounts.get(discounts.size() - 1);

            discountDAO.delete(actualDiscount.getId());

            Assert.assertEquals(expectedDiscount.getClientId(), actualDiscount.getClientId());
            Assert.assertEquals(expectedDiscount.getValue(), actualDiscount.getValue());
            Assert.assertEquals(expectedDiscount.getAdministratorId(), actualDiscount.getAdministratorId());
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if(poolDAO != null){
                try {
                    poolDAO.destroy();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testFindByClientId() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        DiscountDAO discountDAO = null;
        try {
            daoFactory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
            poolDAO = daoFactory.getPoolDAO();
            discountDAO = daoFactory.getDiscountDAO();

            poolDAO.init();

            List<Discount> discounts = discountDAO.findAll();
            Discount discount = discounts.get(discounts.size() - 1);
            int expectedCount = discounts.size() - 1;

            discountDAO.delete(discount.getId());

            discounts = discountDAO.findAll();

            int actualCount = discounts.size();

            discountDAO.insert(discount);

            Assert.assertEquals(expectedCount, actualCount);
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if(poolDAO != null){
                try {
                    poolDAO.destroy();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}