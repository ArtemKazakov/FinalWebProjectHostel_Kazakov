package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.dao.exception.DAOException;

import java.util.Date;
import java.util.List;

/**
 * Provides a DAO-logic for the {@link ScheduleRecord} entity.
 */
public interface ScheduleRecordDAO {

    /**
     * Inserts a new schedule record into a data source.
     *
     * @param scheduleRecord a schedule record object for insertion
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void insert(ScheduleRecord scheduleRecord) throws DAOException;

    /**
     * Updates a schedule record in a data source.
     *
     * @param scheduleRecord a schedule record object for update
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void update(ScheduleRecord scheduleRecord) throws DAOException;

    /**
     * Deletes a schedule record from a data source by id.
     *
     * @param id an id of deleting schedule record
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void delete(int id) throws DAOException;

    /**
     * Deletes a schedule record from a data source by rental request id.
     *
     * @param id an id of rental request of deleting schedule record
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    void deleteByRentalRequestId(int id) throws DAOException;

    /**
     * Gives a list of schedule records from a data source by room id.
     *
     * @param id an room id of desired schedule records
     * @return a {@link List} of schedule records containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<ScheduleRecord> findByRoomId(int id) throws DAOException;

    /**
     * Gives a list of all schedule records from a data source.
     *
     * @return a {@link List} of schedule records
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<ScheduleRecord> findAll() throws DAOException;

    /**
     * Gives a list of all schedule records from a data source.
     *
     * @param start a number from which entries will be returned
     * @param amount of entries
     * @return a {@link List} of schedule records
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<ScheduleRecord> findAllLimited(int start, int amount) throws DAOException;

    /**
     * Gives a list of schedule records from a data source by check in date and check out date.
     *
     * @param checkInDate  a check in date in finding record
     * @param checkOutDate a check out date in finding records
     * @return a {@link List} of schedule records containing the necessary data
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    List<ScheduleRecord> findByDateInterval(Date checkInDate, Date checkOutDate) throws DAOException;

    /**
     * Gives number of schedule records in a data source.
     *
     * @return count of schedule records
     * @throws DAOException in case of some exception with
     *                      a data source or a connection with it
     */
    int selectScheduleRecordCount() throws DAOException;

}
