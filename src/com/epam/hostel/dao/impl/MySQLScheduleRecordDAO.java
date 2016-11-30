package com.epam.hostel.dao.impl;

import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.dao.ScheduleRecordDAO;
import com.epam.hostel.dao.connectionmanager.ConnectionPool;
import com.epam.hostel.dao.connectionmanager.ConnectionPoolException;
import com.epam.hostel.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 16.11.2016.
 */
public class MySQLScheduleRecordDAO implements ScheduleRecordDAO{

    Logger LOGGER = Logger.getRootLogger();

    private static final String INSERT_SCHEDULE_RECORD_QUERY = "INSERT INTO `schedule_records` " +
            "(`id_room`, `id_request`, `checkin_date`, `checkout_date`, `payment_duty`) " +
            "VALUES (?, ?, ?, ?, ?) ";

    private static final String UPDATE_SCHEDULE_RECORD_QUERY = "UPDATE `schedule_records` " +
            "SET `id_room` = ?, `id_request` = ?, `checkin_date` = ?, `checkout_date` = ?, " +
            "`payment_duty` = ? WHERE `id_record` = ? ";

    private static final String SELECT_SCHEDULE_RECORDS_BY_ROOM_ID_QUERY = "SELECT * FROM `schedule_records` " +
            "WHERE `id_room` = ? ";

    private static final String SELECT_ALL_SCHEDULE_RECORDS_QUERY = "SELECT * FROM `schedule_records` ";

    private static final String DELETE_SCHEDULE_RECORD_BY_ID_QUERY = "DELETE FROM `schedule_records` WHERE `id_record` = ? ";

    @Override
    public void insert(ScheduleRecord scheduleRecord) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_SCHEDULE_RECORD_QUERY);

            preparedStatement.setInt(1, scheduleRecord.getRoomId());
            preparedStatement.setInt(2, scheduleRecord.getRequestId());
            preparedStatement.setDate(3, new java.sql.Date(scheduleRecord.getCheckinDate().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(scheduleRecord.getCheckoutDate().getTime()));
            preparedStatement.setInt(5, scheduleRecord.getPaymentDuty());

            preparedStatement.executeUpdate();
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot insert schedule record", e);
        } finally {
            closeStatement(preparedStatement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }
    }

    @Override
    public void update(ScheduleRecord scheduleRecord) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SCHEDULE_RECORD_QUERY);

            preparedStatement.setInt(1, scheduleRecord.getRoomId());
            preparedStatement.setInt(2, scheduleRecord.getRequestId());
            preparedStatement.setDate(3, new java.sql.Date(scheduleRecord.getCheckinDate().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(scheduleRecord.getCheckoutDate().getTime()));
            preparedStatement.setInt(5, scheduleRecord.getPaymentDuty());
            preparedStatement.setInt(6, scheduleRecord.getId());

            preparedStatement.executeUpdate();
        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot update schedule record", e);
        } finally {
            closeStatement(preparedStatement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SCHEDULE_RECORD_BY_ID_QUERY);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot delete schedule record", e);
        } finally {
            closeStatement(preparedStatement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }
    }

    @Override
    public List<ScheduleRecord> findByRoomId(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ScheduleRecord> scheduleRecords = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SCHEDULE_RECORDS_BY_ROOM_ID_QUERY);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ScheduleRecord scheduleRecord = new ScheduleRecord();
                scheduleRecord.setId(resultSet.getInt(1));
                scheduleRecord.setRoomId(resultSet.getInt(2));
                scheduleRecord.setRequestId(resultSet.getInt(3));
                scheduleRecord.setCheckinDate(resultSet.getDate(4));
                scheduleRecord.setCheckoutDate(resultSet.getDate(6));
                scheduleRecord.setPaymentDuty(resultSet.getInt(6));
                scheduleRecords.add(scheduleRecord);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select schedule records by room id", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }

        return scheduleRecords;
    }

    @Override
    public List<ScheduleRecord> findAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<ScheduleRecord> scheduleRecords = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(SELECT_ALL_SCHEDULE_RECORDS_QUERY);

            while(resultSet.next()){
                ScheduleRecord scheduleRecord = new ScheduleRecord();
                scheduleRecord.setId(resultSet.getInt(1));
                scheduleRecord.setRoomId(resultSet.getInt(2));
                scheduleRecord.setRequestId(resultSet.getInt(3));
                scheduleRecord.setCheckinDate(resultSet.getDate(4));
                scheduleRecord.setCheckoutDate(resultSet.getDate(6));
                scheduleRecord.setPaymentDuty(resultSet.getInt(6));
                scheduleRecords.add(scheduleRecord);
            }

        } catch (InterruptedException | ConnectionPoolException e) {
            LOGGER.error("Can not get connection from connection pool");
        } catch (SQLException e){
            throw new DAOException("DAO layer: cannot select all schedule records", e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
            if (connection != null) {
                try {
                    connectionPool.freeConnection(connection);
                } catch (SQLException | ConnectionPoolException e) {
                    LOGGER.error("Can not free connection from connection pool");
                }
            }
        }

        return scheduleRecords;
    }

    public void closeStatement(Statement statement){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e){
            LOGGER.error("Can not close statement");
        }
    }


    public void closeResultSet(ResultSet resultSet){
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e){
            LOGGER.error("Can not close result set");
        }
    }
}
