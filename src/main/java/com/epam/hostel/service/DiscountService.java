package com.epam.hostel.service;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Provides a business-logic with the {@link Discount} entity and relate with it.
 */
public interface DiscountService {

    /**
     * Creates a discount in a data source
     *
     * @param clientId        an id of client who got this discount
     * @param value           a value of discount
     * @param administratorId an id of administrator who gave this discount
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void addDiscount(int clientId, int value, int administratorId) throws ServiceException;

    /**
     * Deletes a discount from a data source by id
     *
     * @param id an id of discount for deleting
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void deleteDiscount(int id) throws ServiceException;

    /**
     * Updates a discount in a data source
     *
     * @param discountId an id of discount for updating
     * @param clientId   an id of client who got this discount
     * @param value      a value of discount
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    void updateDiscount(int discountId, int clientId, int value) throws ServiceException;

    /**
     * Return all discounts from a data source by client id
     * @param id an id of client that received these discounts
     * @return a {@link List} of discounts
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<Discount> getAllDiscountsByClientId(int id) throws ServiceException;

    /**
     * Return all unused discounts from a data source by client id
     * @param id an id of client that received these discounts
     * @return a {@link List} of discounts
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    List<Discount> getAllUnusedDiscountsByClientId(int id) throws ServiceException;
}
