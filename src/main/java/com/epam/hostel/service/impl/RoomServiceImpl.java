package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.dao.RoomDAO;
import com.epam.hostel.dao.ScheduleRecordDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.RoomService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceExistingRoomNumberException;
import com.epam.hostel.service.util.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ASUS on 02.11.2016.
 */
public class RoomServiceImpl implements RoomService {

    @Override
    public void addRoom(int number, int seatsNumber, int perdayCost) throws ServiceException {
        if (!Validator.validateRoomNumber(number) || !Validator.validateSeatsNumber(seatsNumber)
                || !Validator.validatePerdayCost(perdayCost)) {
            throw new ServiceException("Wrong parameters for adding room");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RoomDAO roomDAO = factory.getRoomDAO();

        try {
            Room roomWithThisNumber = roomDAO.findByNumber(number);
            if (roomWithThisNumber != null) {
                throw new ServiceExistingRoomNumberException("Existing room number");
            }

            Room room = new Room();
            room.setNumber(number);
            room.setSeatsNumber(seatsNumber);
            room.setPerdayCost(perdayCost);

            roomDAO.insert(room);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot add a room", e);
        }
    }

    @Override
    public void updateRoom(int originalNumber, int number, int seatsNumber, int perdayCost) throws ServiceException {
        if (!Validator.validateRoomNumber(originalNumber) || !Validator.validateRoomNumber(number) ||
                !Validator.validateSeatsNumber(seatsNumber) || !Validator.validatePerdayCost(perdayCost)) {
            throw new ServiceException("Wrong parameters for editing room");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RoomDAO roomDAO = factory.getRoomDAO();

        Room room = new Room();
        room.setNumber(number);
        room.setSeatsNumber(seatsNumber);
        room.setPerdayCost(perdayCost);

        try {
            if (originalNumber != number) {
                Room roomWithThisNumber = roomDAO.findByNumber(number);
                if (roomWithThisNumber != null) {
                    throw new ServiceExistingRoomNumberException("Existing room number");
                }

                roomDAO.insert(room);
                roomDAO.delete(originalNumber);
            } else {
                roomDAO.update(room);
            }
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot edit a room", e);
        }
    }

    @Override
    public void deleteRoom(int number) throws ServiceException {
        if (!Validator.validateRoomNumber(number)) {
            throw new ServiceException("Wrong parameter for deleting room");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RoomDAO roomDAO = factory.getRoomDAO();

        try {
            roomDAO.delete(number);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete a room", e);
        }
    }

    @Override
    public List<Room> getAllRooms() throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RoomDAO dao = factory.getRoomDAO();
        List<Room> rooms = null;

        try {
            rooms = dao.findAll();
            return rooms;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all rooms", e);
        }
    }

    @Override
    public List<Room> getAllRoomsBySeatsNumber(int seatsNumber) throws ServiceException {
        if (!Validator.validateSeatsNumber(seatsNumber)) {
            throw new ServiceException("Wrong seats number for getting rooms");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RoomDAO dao = factory.getRoomDAO();
        List<Room> rooms = null;

        try {
            rooms = dao.findBySeatsNumber(seatsNumber);
            return rooms;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get rooms by seats number", e);
        }
    }

    @Override
    public List<Room> getAllSuitableRooms(Date checkInDate, int daysStayNumber, int seatsNumber) throws ServiceException {

        if (checkInDate == null || !Validator.validateDaysStayNumber(daysStayNumber) ||
                !Validator.validateSeatsNumber(seatsNumber)) {
            throw new ServiceException("Wrong parameters for getting all suitable rooms");
        }
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RoomDAO roomDAO = factory.getRoomDAO();
        ScheduleRecordDAO recordDAO = factory.getScheduleRecordDAO();
        List<ScheduleRecord> scheduleRecords = null;
        List<Room> rooms = null;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkInDate);
            calendar.add(Calendar.DATE, daysStayNumber);
            Date checkOutDate = calendar.getTime();
            scheduleRecords = recordDAO.findByDateInterval(checkInDate, checkOutDate);

            rooms = roomDAO.findBySeatsNumber(seatsNumber);

            if ((rooms != null) && (scheduleRecords != null)) {
                for (ScheduleRecord record : scheduleRecords) {
                    for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); )
                        if (iterator.next().getNumber() == record.getRoomNumber())
                            iterator.remove();
                }
            }

            return rooms;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all suitable rooms", e);
        }
    }
}
