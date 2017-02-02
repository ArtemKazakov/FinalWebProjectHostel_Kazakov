package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.dao.RoomDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Provides a DAO-logic for the {@link Room} entity for the MySQL Database.
 */
public class MySQLRoomDAO extends MySQLDAO implements RoomDAO {

    private static final String INSERT_ROOM_QUERY = "INSERT INTO `rooms` " +
            "(`id_room`, `seats_number`, `perday_cost`) " +
            "VALUES (?, ?, ?) ";

    private static final String UPDATE_ROOM_QUERY = "UPDATE `rooms` " +
            "SET `seats_number` = ?, `perday_cost` = ? " +
            "WHERE `id_room` = ? ";

    private static final String DELETE_ROOM_BY_ID_QUERY = "DELETE FROM `rooms` WHERE `id_room` = ? ";

    private static final String SELECT_ROOM_BY_NUMBER_QUERY = "SELECT * FROM `rooms` WHERE `id_room` = ? ";

    private static final String SELECT_ALL_ROOMS_QUERY = "SELECT * FROM `rooms` LIMIT ?, ?;";

    private static final String SELECT_ROOMS_BY_SEATS_NUMBER_LIMITED_QUERY = "SELECT * FROM `rooms` WHERE `seats_number` = ? LIMIT ?, ?;";

    private static final String SELECT_ROOMS_BY_SEATS_NUMBER_QUERY = "SELECT * FROM `rooms` WHERE `seats_number` = ? ";

    private static final String SELECT_COUNT_OF_ROOMS = "SELECT COUNT(*) FROM `rooms`;";

    private static final String SELECT_COUNT_OF_ROOMS_BY_SEATS_NUMBER = "SELECT COUNT(*) FROM `rooms` WHERE `seats_number` = ? ;";

    private DataSource dataSource = (DataSource) TransactionManagerImpl.getInstance();

    /**
     * Set a {@link DataSource} object, that will give a {@link Connection}
     * for all operation with the database.
     *
     * @param dataSource for setting
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Inserts a new room into a database.
     *
     * @param room a room object for insertion
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void insert(Room room) throws DAOException {
        doDataManipulation(dataSource, INSERT_ROOM_QUERY, "DAO layer: cannot insert room",
                preparedStatement -> {
                    preparedStatement.setInt(1, room.getNumber());
                    preparedStatement.setInt(2, room.getSeatsNumber());
                    preparedStatement.setInt(3, room.getPerdayCost());
                }
        );
    }

    /**
     * Updates a room in a database.
     *
     * @param room a room object for update
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void update(Room room) throws DAOException {
        doDataManipulation(dataSource, UPDATE_ROOM_QUERY, "DAO layer: cannot update room",
                preparedStatement -> {
                    preparedStatement.setInt(1, room.getSeatsNumber());
                    preparedStatement.setInt(2, room.getPerdayCost());
                    preparedStatement.setInt(3, room.getNumber());
                }
        );
    }

    /**
     * Deletes a room from a database by id.
     *
     * @param id an id of deleting room
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void delete(int id) throws DAOException {
        doDataManipulation(dataSource, DELETE_ROOM_BY_ID_QUERY, "DAO layer: cannot delete room",
                preparedStatement -> preparedStatement.setInt(1, id)
        );
    }

    /**
     * Gives a room from a database by number.
     *
     * @param number an id of a desired room
     * @return a room object containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public Room findByNumber(int number) throws DAOException {
        return singleSelect(dataSource, SELECT_ROOM_BY_NUMBER_QUERY, "DAO layer: cannot get room by number",
                preparedStatement -> preparedStatement.setInt(1, number),
                resultSet -> resultSet.next() ? this.createRoom(resultSet) : null
        );
    }

    /**
     * Gives a list of all rooms from a database.
     *
     * @param start  a number from which entries will be returned
     * @param amount of entries
     * @return a {@link List} of rooms
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<Room> findAll(int start, int amount) throws DAOException {
        return select(dataSource, SELECT_ALL_ROOMS_QUERY, "DAO layer: cannot get all rooms",
                preparedStatement -> {
                    preparedStatement.setInt(1, start);
                    preparedStatement.setInt(2, amount);
                },
                this::createRoom
        );
    }

    /**
     * Gives a list of rooms from a database by seats number.
     *
     * @param start       a number from which entries will be returned
     * @param amount      of entries
     * @param seatsNumber a seats number of desired rooms
     * @return a {@link List} of rooms containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<Room> findBySeatsNumberLimited(int start, int amount, int seatsNumber) throws DAOException {
        return select(dataSource, SELECT_ROOMS_BY_SEATS_NUMBER_LIMITED_QUERY, "DAO layer: cannot get rooms by seats number",
                preparedStatement -> {
                    preparedStatement.setInt(1, seatsNumber);
                    preparedStatement.setInt(2, start);
                    preparedStatement.setInt(3, amount);
                },
                this::createRoom
        );
    }

    /**
     * Gives a list of rooms from a database by seats number.
     *
     * @param seatsNumber a seats number of desired rooms
     * @return a {@link List} of rooms containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<Room> findBySeatsNumber(int seatsNumber) throws DAOException {
        return select(dataSource, SELECT_ROOMS_BY_SEATS_NUMBER_QUERY, "DAO layer: cannot get rooms by seats number",
                preparedStatement -> preparedStatement.setInt(1, seatsNumber),
                this::createRoom
        );
    }

    /**
     * Gives number of rooms in a database.
     *
     * @return count of rooms
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    @Override
    public int selectRoomCount() throws DAOException {
        return this.singleSelect(
                dataSource,
                SELECT_COUNT_OF_ROOMS,
                "Can't get count of rooms",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getInt(1);
                }
        );
    }

    /**
     * Gives number of rooms in a database.
     *
     * @return count of rooms
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    @Override
    public int selectRoomCountBySeatsNumber(int seatsNumber) throws DAOException {
        return this.singleSelect(
                dataSource,
                SELECT_COUNT_OF_ROOMS_BY_SEATS_NUMBER,
                "Can't get count of rooms by seats number",
                preparedStatement -> preparedStatement.setInt(1, seatsNumber),
                resultSet -> {
                    resultSet.next();
                    return resultSet.getInt(1);
                }
        );
    }

    /**
     * Creates a new {@link Room} object.
     *
     * @param resultSet a {@link ResultSet} object from which information will be extracted
     * @return a room object
     * @throws SQLException in cases of errors
     */
    private Room createRoom(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        room.setNumber(resultSet.getInt(1));
        room.setSeatsNumber(resultSet.getInt(2));
        room.setPerdayCost(resultSet.getInt(3));
        return room;
    }
}
