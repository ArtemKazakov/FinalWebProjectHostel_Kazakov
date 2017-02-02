package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.bean.entity.User;
import com.epam.hostel.dao.RequestDAO;
import com.epam.hostel.dao.ScheduleRecordDAO;
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

public class MySQLScheduleRecordDAOTest {
    private static final String DAO_CONFIGURATION = "/bean/daobeans.xml";

    private static ScheduleRecordDAO dao;
    private static TransactionManager transactionManager = TransactionManagerImpl.getInstance();

    @BeforeClass
    public static void init() {
        DAOFactory.getInstance().inject(DAO_CONFIGURATION);
        dao = DAOFactory.getInstance().getScheduleRecordDAO();
        try {
            DAOFactory.getInstance().getPoolDAO().init();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testInsert() throws Exception {
        try {
            RequestDAO requestDAO = DAOFactory.getInstance().getRequestDAO();
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
            transactionManager.doInTransaction(() -> {
                int id = requestDAO.insert(rentalRequest);

                ScheduleRecord scheduleRecord = new ScheduleRecord();
                scheduleRecord.setRoomNumber(101);
                scheduleRecord.setRequestId(id);
                scheduleRecord.setCheckInDate(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, 4);
                scheduleRecord.setCheckoutDate(cal.getTime());
                scheduleRecord.setPaymentDuty(0);

                dao.insert(scheduleRecord);

                List<ScheduleRecord> scheduleRecords = dao.findAll();
                ScheduleRecord actualScheduleRecord = scheduleRecords.get(scheduleRecords.size() - 1);

                Assert.assertEquals(scheduleRecord.getRoomNumber(), actualScheduleRecord.getRoomNumber());
                Assert.assertEquals(scheduleRecord.getRequestId(), actualScheduleRecord.getRequestId());
                Assert.assertEquals(scheduleRecord.getPaymentDuty(), actualScheduleRecord.getPaymentDuty());

                dao.delete(actualScheduleRecord.getId());

                requestDAO.delete(id);

                return Optional.empty();
            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFindAll() throws Exception {
        try {
            List<ScheduleRecord> list = transactionManager.doInTransaction(dao :: findAll);
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