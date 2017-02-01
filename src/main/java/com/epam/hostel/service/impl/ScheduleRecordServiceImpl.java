package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.dao.ScheduleRecordDAO;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.ScheduleRecordService;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Provides a business-logic with the {@link ScheduleRecord} entity and relate with it.
 */
public class ScheduleRecordServiceImpl extends Service implements ScheduleRecordService {
    private final ScheduleRecordDAO scheduleRecordDAO = DAOFactory.getInstance().getScheduleRecordDAO();

    /**
     * Return all schedule records from a data source
     *
     * @return a {@link List} of schedule records
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public List<ScheduleRecord> getAllScheduleRecords() throws ServiceException {
        return this.service("Service layer: cannot get all schedule records",
                scheduleRecordDAO::findAll
        );
    }
}
