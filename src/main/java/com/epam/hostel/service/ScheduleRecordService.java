package com.epam.hostel.service;

import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Provides a business-logic with the {@link ScheduleRecord} entity and relate with it.
 */
public interface ScheduleRecordService {

    /**
     * Return all schedule records from a data source
     *
     * @return a {@link List} of schedule records
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<ScheduleRecord> getAllScheduleRecords() throws ServiceException;
}
