package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.dao.RoomDAO;
import com.epam.hostel.dao.ScheduleRecordDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.dao.transaction.TransactionManager;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;
import com.epam.hostel.service.RoomService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.exception.ServiceExistingRoomNumberException;
import com.epam.hostel.service.util.Validator;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Provides a business-logic with the {@link Room} entity and relate with it.
 */
public class RoomServiceImpl extends Service implements RoomService {
    private static final Logger logger = Logger.getLogger(RoomServiceImpl.class);
    private final RoomDAO roomDAO = DAOFactory.getInstance().getRoomDAO();
    private final ScheduleRecordDAO scheduleRecordDAO = DAOFactory.getInstance().getScheduleRecordDAO();
    private static TransactionManager transactionManager = TransactionManagerImpl.getInstance();

    /**
     * Creates a room in a data source
     *
     * @param number      a number of the room
     * @param seatsNumber a seats number of the room
     * @param perdayCost  a per day cost of the room
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void addRoom(int number, int seatsNumber, int perdayCost) throws ServiceException {
        if (!Validator.validateRoomNumber(number) || !Validator.validateSeatsNumber(seatsNumber)
                || !Validator.validatePerdayCost(perdayCost)) {
            throw new ServiceException("Wrong parameters for adding room");
        }

        try {
            transactionManager.doInTransaction(() -> {
                Room roomWithThisNumber = roomDAO.findByNumber(number);

                if (roomWithThisNumber != null) {
                    throw new ServiceExistingRoomNumberException("Existing room number");
                }

                Room room = createRoom(number, seatsNumber, perdayCost);

                roomDAO.insert(room);
                return Optional.empty();
            });
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot add a room", e);
        }
    }

    /**
     * Updates a room in a data source
     *
     * @param originalNumber an original number of the room
     * @param number         a new number of the room
     * @param seatsNumber    a seats number of the room
     * @param perdayCost     a per day cost of the room
     * @throws ServiceException n case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void updateRoom(int originalNumber, int number, int seatsNumber, int perdayCost) throws ServiceException {
        if (!Validator.validateRoomNumber(originalNumber) || !Validator.validateRoomNumber(number) ||
                !Validator.validateSeatsNumber(seatsNumber) || !Validator.validatePerdayCost(perdayCost)) {
            throw new ServiceException("Wrong parameters for editing room");
        }

        Room room = createRoom(number, seatsNumber, perdayCost);

        try {
            if (originalNumber != number) {
                transactionManager.doInTransaction(() -> {
                    Room roomWithThisNumber = roomDAO.findByNumber(number);
                    if (roomWithThisNumber != null) {
                        throw new ServiceExistingRoomNumberException("Existing room number");
                    }

                    roomDAO.insert(room);
                    roomDAO.delete(originalNumber);

                    return Optional.empty();
                });
            } else {
                transactionManager.doInTransaction(() -> {
                    roomDAO.update(room);
                    return Optional.empty();
                });
            }
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot edit a room", e);
        }
    }

    /**
     * Deletes a room from a data source by number
     *
     * @param number a number of room for deleting
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void deleteRoom(int number) throws ServiceException {
        if (!Validator.validateRoomNumber(number)) {
            throw new ServiceException("Wrong parameter for deleting room");
        }

        this.service("Service layer: cannot delete a room",
                () -> {
                    roomDAO.delete(number);
                    return Optional.empty();
                }
        );
    }

    /**
     * Return all rooms from a data source
     *
     * @param start  the number from which accounts will be returned
     * @param amount of rooms
     * @return a {@link List} of rooms
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public List<Room> getAllRooms(int start, int amount) throws ServiceException {
        if (!Validator.validateStart(start) ||
                !Validator.validateInt(amount)) {
            throw new ServiceException("Wrong seats number for getting all rooms");
        }

        return this.service("Service layer: cannot get all rooms",
                () -> roomDAO.findAll(start, amount)
        );
    }

    /**
     * Return all rooms from a data source by seats number
     *
     * @param start       the number from which accounts will be returned
     * @param amount      of rooms
     * @param seatsNumber a seats number of finding rooms
     * @return a {@link List} of rooms
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public List<Room> getAllRoomsBySeatsNumber(int start, int amount, int seatsNumber) throws ServiceException {
        if (!Validator.validateSeatsNumber(seatsNumber) ||
                !Validator.validateStart(start) ||
                !Validator.validateInt(amount)) {
            throw new ServiceException("Wrong seats number for getting rooms by seats number");
        }

        return this.service("Service layer: cannot get all rooms by seats number",
                () -> roomDAO.findBySeatsNumberLimited(start, amount, seatsNumber)
        );
    }

    /**
     * Return all rooms from a data source by check in date,
     * days stay number and seats number
     *
     * @param checkInDate    a check in date
     * @param daysStayNumber a days stay number
     * @param seatsNumber    a seats number of finding rooms
     * @return a {@link List} of rooms
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public List<Room> getAllSuitableRooms(Date checkInDate, int daysStayNumber, int seatsNumber) throws ServiceException {

        if (!Validator.validateCheckinDate(checkInDate) ||
                !Validator.validateDaysStayNumber(daysStayNumber) ||
                !Validator.validateSeatsNumber(seatsNumber)) {
            throw new ServiceException("Wrong parameters for getting all suitable rooms");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        calendar.add(Calendar.DATE, daysStayNumber);
        Date checkOutDate = calendar.getTime();

        try {
            List<ScheduleRecord> scheduleRecords = transactionManager.doInTransaction(() ->
                    scheduleRecordDAO.findByDateInterval(checkInDate, checkOutDate)
            );
            List<Room> rooms = transactionManager.doInTransaction(() ->
                    roomDAO.findBySeatsNumber(seatsNumber)
            );

            if ((rooms != null) && (scheduleRecords != null)) {
                for (ScheduleRecord record : scheduleRecords) {
                    for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); )
                        if (iterator.next().getNumber() == record.getRoomNumber())
                            iterator.remove();
                }
            }

            return rooms;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot get all suitable rooms", e);
        }
    }

    /**
     * Returns number of rooms in data source.
     *
     * @return amount of rooms
     * @throws ServiceException if error occurred with data source
     */
    @Override
    public int getRoomsCount() throws ServiceException {
        return this.service("Service layer: cannot get count of rooms",
                roomDAO::selectRoomCount
        );
    }

    /**
     * Returns number of rooms in data source.
     *
     * @param seatsNumber a seats number of finding rooms
     * @return amount of rooms
     * @throws ServiceException if error occurred with data source
     */
    @Override
    public int getRoomsCountBySeatsNumber(int seatsNumber) throws ServiceException {
        if (!Validator.validateSeatsNumber(seatsNumber)) {
            throw new ServiceException("Wrong parameters for getting count of rooms by seats number");
        }
        return this.service("Service layer: cannot get count of rooms",
                () -> roomDAO.selectRoomCountBySeatsNumber(seatsNumber)
        );
    }

    /**
     * Creates a {@link Room} object
     *
     * @param number      a number of the room
     * @param seatsNumber a seats number of the room
     * @param perdayCost  a per day cost of the room
     * @return a room
     */
    private Room createRoom(int number, int seatsNumber, int perdayCost) {
        Room room = new Room();
        room.setNumber(number);
        room.setSeatsNumber(seatsNumber);
        room.setPerdayCost(perdayCost);
        return room;
    }
}
