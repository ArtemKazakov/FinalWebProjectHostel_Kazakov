package com.epam.hostel.service;

import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Provides a business-logic with the {@link RentalRequest} entity and relate with it.
 */
public interface RentalRequestService {

    /**
     * Makes a new rental request and a schedule record in a data source
     *
     * @param scheduleRecord a {@link ScheduleRecord} object
     * @param rentalRequest  a {@link RentalRequest} object
     * @param discountId     an id of used discount
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void makeRentalRequest(ScheduleRecord scheduleRecord, RentalRequest rentalRequest, int discountId) throws ServiceException;

    /**
     * Updates a rental request in a data source
     *
     * @param id              an id of updating rental request
     * @param accepted        an accept status of request
     * @param administratorId an administrator id who update state of request
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void updateRentalRequest(int id, boolean accepted, int administratorId) throws ServiceException;

    /**
     * Deletes a rental request from a data source by id
     *
     * @param id an id of rental request for deleting
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void deleteRentalRequest(int id) throws ServiceException;

    /**
     * Return a rental request from a data source by id
     *
     * @param id an id of rental request
     * @return a rental request
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    RentalRequest getRentalRequestById(int id) throws ServiceException;

    /**
     * Return all rental requests from a data source
     *
     * @return a {@link List} of rental requests
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<RentalRequest> getAllRentalRequests() throws ServiceException;

    /**
     * Return all rental requests from a data source
     *
     * @param start       the number from which accounts will be returned
     * @param amount      of rental requests
     * @return a {@link List} of rental requests
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<RentalRequest> getAllRentalRequestsLimited(int start, int amount) throws ServiceException;

    /**
     * Return all rental requests from a data source by client id
     *
     * @param id an id of client that make rental requests
     * @return a {@link List} of discounts
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<RentalRequest> getAllRentalRequestsByClientId(int id) throws ServiceException;

    /**
     * Returns number of rental requests in data source.
     *
     * @return amount of rental requests
     * @throws ServiceException if error occurred with data source
     */
    int getRentalRequestsCount() throws ServiceException;

}
