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

    /**
     * Return all schedule records from a data source
     *
     * @param start  the number from which accounts will be returned
     * @param amount of schedule records
     * @return a {@link List} of schedule records
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<ScheduleRecord> getAllScheduleRecordsLimited(int start, int amount) throws ServiceException;

    /**
     * Returns number of schedule records in data source.
     *
     * @return amount of schedule records
     * @throws ServiceException if error occurred with data source
     */
    int getScheduleRecordsCount() throws ServiceException;
}
