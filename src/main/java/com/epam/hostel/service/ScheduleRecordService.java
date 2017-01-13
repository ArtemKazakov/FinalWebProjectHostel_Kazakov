package com.epam.hostel.service;

import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Created by ASUS on 03.12.2016.
 */
public interface ScheduleRecordService {

    List<ScheduleRecord> getAllScheduleRecords() throws ServiceException;
}
