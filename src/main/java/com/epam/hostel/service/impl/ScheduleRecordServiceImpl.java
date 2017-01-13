package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.dao.ScheduleRecordDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.ScheduleRecordService;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Created by ASUS on 03.12.2016.
 */
public class ScheduleRecordServiceImpl implements ScheduleRecordService {

    @Override
    public List<ScheduleRecord> getAllScheduleRecords() throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        ScheduleRecordDAO dao = factory.getScheduleRecordDAO();
        List<ScheduleRecord> scheduleRecords = null;

        try{
            scheduleRecords = dao.findAll();
            return scheduleRecords;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all schedule records", e);
        }
    }
}
