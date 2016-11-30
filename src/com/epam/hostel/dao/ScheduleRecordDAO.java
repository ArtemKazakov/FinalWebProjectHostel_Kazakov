package com.epam.hostel.dao;

import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.dao.exception.DAOException;

import java.util.List;

/**
 * Created by ASUS on 16.11.2016.
 */
public interface ScheduleRecordDAO {

    void insert(ScheduleRecord scheduleRecord) throws DAOException;

    void update(ScheduleRecord scheduleRecord) throws DAOException;

    void delete(int id) throws DAOException;

    List<ScheduleRecord> findByRoomId(int id) throws DAOException;

    List<ScheduleRecord> findAll() throws DAOException;
}
