package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.dao.RoomDAO;
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

public class MySQLRoomDAOTest {
    private static final String DAO_CONFIGURATION = "/bean/daobeans.xml";

    private static RoomDAO dao;
    private static TransactionManager transactionManager = TransactionManagerImpl.getInstance();

    @BeforeClass
    public static void init() {
        DAOFactory.getInstance().inject(DAO_CONFIGURATION);
        dao = DAOFactory.getInstance().getRoomDAO();
        try {
            DAOFactory.getInstance().getPoolDAO().init();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testInsert() throws Exception {
        try {
            Room expectedRoom = new Room();
            expectedRoom.setNumber(228);
            expectedRoom.setSeatsNumber(3);
            expectedRoom.setPerdayCost(30);
            transactionManager.doInTransaction(() -> {
                dao.insert(expectedRoom);

                List<Room> rooms = dao.findAll(0, 1000);
                Room actualRoom = rooms.get(rooms.size() - 1);

                dao.delete(actualRoom.getNumber());

                Assert.assertEquals(expectedRoom.getNumber(), actualRoom.getNumber());
                Assert.assertEquals(expectedRoom.getPerdayCost(), actualRoom.getPerdayCost());
                Assert.assertEquals(expectedRoom.getSeatsNumber(), actualRoom.getSeatsNumber());

                return Optional.empty();
            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate() throws Exception {
        try {
            Room room = new Room();
            room.setNumber(228);
            room.setSeatsNumber(3);
            room.setPerdayCost(30);
            transactionManager.doInTransaction(() -> {
                dao.insert(room);

                List<Room> rooms = dao.findAll(0, 1000);
                Room insertedRoom = rooms.get(rooms.size() - 1);

                insertedRoom.setSeatsNumber(4);
                insertedRoom.setPerdayCost(12);

                dao.update(insertedRoom);

                insertedRoom = dao.findByNumber(insertedRoom.getNumber());

                Assert.assertEquals(insertedRoom.getSeatsNumber(), 4);
                Assert.assertEquals(insertedRoom.getPerdayCost(), 12);

                dao.delete(insertedRoom.getNumber());

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
                List<Room> rooms = dao.findAll(0, 1000);
                Room room = rooms.get(rooms.size() - 1);
                int expectedCount = rooms.size() - 1;
                dao.delete(room.getNumber());
                rooms = dao.findAll(0, 1000);
                int actualCount = rooms.size();
                dao.insert(room);
                Assert.assertEquals(expectedCount, actualCount);

                return Optional.empty();
            });
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByNumber() throws Exception {
        try {
            Room room = transactionManager.doInTransaction(() -> dao.findByNumber(101));
            assertNotNull(room);
            assertTrue(room.getNumber() == 101);
        } catch(DAOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAll() throws Exception {
        try {
            List<Room> list = transactionManager.doInTransaction(() -> dao.findAll(0, 10));
            assertNotNull(list);
            assertTrue(list.size() > 0);
        } catch(DAOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindBySeatsNumber() throws Exception {
        try {
            transactionManager.doInTransaction(() -> {
                Room room = new Room();
                room.setNumber(228);
                room.setSeatsNumber(3);
                room.setPerdayCost(30);
                dao.insert(room);
                List<Room> rooms = dao.findBySeatsNumber(3);
                assertNotNull(rooms);
                assertTrue(rooms.size() > 0);

                dao.delete(rooms.get(rooms.size() - 1).getNumber());

                return Optional.empty();
            });
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