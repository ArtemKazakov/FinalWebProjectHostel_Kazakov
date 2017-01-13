package com.epam.hostel.service;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Created by ASUS on 04.12.2016.
 */
public interface RentalRequestService {

    void makeRentalRequest(ScheduleRecord scheduleRecord, RentalRequest rentalRequest, int discountId) throws ServiceException;

    void updateRentalRequest(int id, boolean accepted, int administratorId) throws ServiceException;

    void deleteRentalRequest(int id) throws ServiceException;

    RentalRequest getRentalRequestById(int id) throws ServiceException;

    List<RentalRequest> getAllRentalRequests() throws ServiceException;

    List<RentalRequest> getAllRentalRequestsByClientId(int id) throws ServiceException;

}
