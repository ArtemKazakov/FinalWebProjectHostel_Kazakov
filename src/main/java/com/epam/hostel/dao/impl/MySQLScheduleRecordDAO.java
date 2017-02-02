package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.dao.ScheduleRecordDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Provides a DAO-logic for the {@link ScheduleRecord} entity for the MySQL Database.
 */
public class MySQLScheduleRecordDAO extends MySQLDAO implements ScheduleRecordDAO {

    private static final String INSERT_SCHEDULE_RECORD_QUERY = "INSERT INTO `sсhedule_records` " +
            "(`id_room`, `id_request`, `checkin_date`, `checkout_date`, `payment_duty`) " +
            "VALUES (?, ?, ?, ?, ?) ";

    private static final String UPDATE_SCHEDULE_RECORD_QUERY = "UPDATE `sсhedule_records` " +
            "SET `id_room` = ?, `id_request` = ?, `checkin_date` = ?, `checkout_date` = ?, " +
            "`payment_duty` = ? WHERE `id_record` = ? ";

    private static final String SELECT_SCHEDULE_RECORDS_BY_ROOM_ID_QUERY = "SELECT * FROM `sсhedule_records` " +
            "WHERE `id_room` = ? ";

    private static final String SELECT_SCHEDULE_RECORDS_BY_CHECK_IN_DATE_AND_CHECK_OUT_DATE_QUERY = "SELECT * FROM `sсhedule_records` " +
            "WHERE `checkin_date` <= ? AND `checkout_date` >= ?";

    private static final String SELECT_ALL_SCHEDULE_RECORDS_QUERY = "SELECT * FROM `sсhedule_records` ";

    private static final String SELECT_ALL_SCHEDULE_RECORDS_LIMITED_QUERY = "SELECT * FROM `sсhedule_records` LIMIT ?, ?;";

    private static final String DELETE_SCHEDULE_RECORD_BY_ID_QUERY = "DELETE FROM `sсhedule_records` WHERE `id_record` = ? ";

    private static final String DELETE_SCHEDULE_RECORD_BY_RENTAL_REQUEST_ID_QUERY = "DELETE FROM `sсhedule_records` WHERE `id_request` = ? ";

    private static final String SELECT_COUNT_OF_SCHEDULE_RECORDS = "SELECT COUNT(*) FROM `sсhedule_records`;";

    private DataSource dataSource = (DataSource) TransactionManagerImpl.getInstance();

    /**
     * Set a {@link DataSource} object, that will give a {@link Connection}
     * for all operation with the database.
     * @param dataSource for setting
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Inserts a new schedule record into a database.
     *
     * @param scheduleRecord a schedule record object for insertion
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void insert(ScheduleRecord scheduleRecord) throws DAOException {
        doDataManipulation(dataSource, INSERT_SCHEDULE_RECORD_QUERY, "DAO layer: cannot insert schedule record",
                preparedStatement -> {
                    preparedStatement.setInt(1, scheduleRecord.getRoomNumber());
                    preparedStatement.setInt(2, scheduleRecord.getRequestId());
                    preparedStatement.setDate(3, new Date(scheduleRecord.getCheckInDate().getTime()));
                    preparedStatement.setDate(4, new Date(scheduleRecord.getCheckoutDate().getTime()));
                    preparedStatement.setInt(5, scheduleRecord.getPaymentDuty());
                }
        );
    }

    /**
     * Updates a schedule record in a database.
     *
     * @param scheduleRecord a schedule record object for update
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void update(ScheduleRecord scheduleRecord) throws DAOException {
        doDataManipulation(dataSource, UPDATE_SCHEDULE_RECORD_QUERY, "DAO layer: cannot update schedule record",
                preparedStatement -> {
                    preparedStatement.setInt(1, scheduleRecord.getRoomNumber());
                    preparedStatement.setInt(2, scheduleRecord.getRequestId());
                    preparedStatement.setDate(3, new Date(scheduleRecord.getCheckInDate().getTime()));
                    preparedStatement.setDate(4, new Date(scheduleRecord.getCheckoutDate().getTime()));
                    preparedStatement.setInt(5, scheduleRecord.getPaymentDuty());
                    preparedStatement.setInt(6, scheduleRecord.getId());
                }
        );
    }

    /**
     * Deletes a schedule record from a database by id.
     *
     * @param id an id of deleting schedule record
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void delete(int id) throws DAOException {
        doDataManipulation(dataSource, DELETE_SCHEDULE_RECORD_BY_ID_QUERY, "DAO layer: cannot delete schedule record",
                preparedStatement -> preparedStatement.setInt(1, id)
        );
    }

    /**
     * Deletes a schedule record from a database by rental request id.
     *
     * @param id an id of rental request of deleting schedule record
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public void deleteByRentalRequestId(int id) throws DAOException {
        doDataManipulation(dataSource, DELETE_SCHEDULE_RECORD_BY_RENTAL_REQUEST_ID_QUERY, "DAO layer: cannot delete schedule record by rental request id",
                preparedStatement -> preparedStatement.setInt(1, id)
        );
    }

    /**
     * Gives a list of schedule records from a database by room id.
     *
     * @param id an room id of desired schedule records
     * @return a {@link List} of schedule records containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<ScheduleRecord> findByRoomId(int id) throws DAOException {
        return select(dataSource, SELECT_SCHEDULE_RECORDS_BY_ROOM_ID_QUERY, "DAO layer: cannot select schedule records by room id",
                preparedStatement -> preparedStatement.setInt(1, id),
                this :: createScheduleRecord
        );
    }

    /**
     * Gives a list of all schedule records from a database.
     *
     * @return a {@link List} of schedule records
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<ScheduleRecord> findAll() throws DAOException {
        return select(dataSource, SELECT_ALL_SCHEDULE_RECORDS_QUERY, "DAO layer: cannot select all schedule records",
                this :: createScheduleRecord
        );
    }

    /**
     * Gives a list of all schedule records from a database.
     *
     * @param start a number from which entries will be returned
     * @param amount of entries
     * @return a {@link List} of schedule records
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<ScheduleRecord> findAllLimited(int start, int amount) throws DAOException {
        return select(dataSource, SELECT_ALL_SCHEDULE_RECORDS_LIMITED_QUERY, "DAO layer: cannot select all schedule records",
                preparedStatement -> {
                    preparedStatement.setInt(1, start);
                    preparedStatement.setInt(2, amount);
                },
                this :: createScheduleRecord
        );
    }

    /**
     * Gives a list of schedule records from a database by check in date and check out date.
     *
     * @param checkInDate  a check in date in finding record
     * @param checkOutDate a check out date in finding records
     * @return a {@link List} of schedule records containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a database or a connection with it
     */
    @Override
    public List<ScheduleRecord> findByDateInterval(java.util.Date checkInDate, java.util.Date checkOutDate) throws DAOException {
        return select(dataSource, SELECT_SCHEDULE_RECORDS_BY_CHECK_IN_DATE_AND_CHECK_OUT_DATE_QUERY, "DAO layer: cannot select schedule records by date interval",
                preparedStatement -> {
                    preparedStatement.setDate(1, new Date(checkOutDate.getTime()));
                    preparedStatement.setDate(2, new Date(checkInDate.getTime()));
                },
                this :: createScheduleRecord
        );
    }

    /**
     * Gives number of schedule records in a database.
     *
     * @return count of schedule records
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    @Override
    public int selectScheduleRecordCount() throws DAOException {
        return this.singleSelect(
                dataSource,
                SELECT_COUNT_OF_SCHEDULE_RECORDS,
                "Can't get count of schedule records",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getInt(1);
                }
        );
    }

    /**
     * Creates a new {@link ScheduleRecord} object.
     * @param resultSet a {@link ResultSet} object from which information will be extracted
     * @return a schedule record object
     * @throws SQLException in cases of errors
     */
    private ScheduleRecord createScheduleRecord(ResultSet resultSet) throws SQLException {
        ScheduleRecord scheduleRecord = new ScheduleRecord();
        scheduleRecord.setId(resultSet.getInt(1));
        scheduleRecord.setRoomNumber(resultSet.getInt(2));
        scheduleRecord.setRequestId(resultSet.getInt(3));
        scheduleRecord.setCheckInDate(resultSet.getDate(4));
        scheduleRecord.setCheckoutDate(resultSet.getDate(5));
        scheduleRecord.setPaymentDuty(resultSet.getInt(6));
        return scheduleRecord;
    }

}
