package com.epam.hostel.service;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Created by ASUS on 18.11.2016.
 */
public interface DiscountService {

    void addDiscount(int clientId, int value, int administratorId) throws ServiceException;

    void deleteDiscount(int id) throws ServiceException;

    void updateDiscount(int discountId, int clientId, int value) throws ServiceException;

    List<Discount> getAllDiscountsByClientId(int id) throws ServiceException;

    List<Discount> getAllUnusedDiscountsByClientId(int id) throws ServiceException;
}
