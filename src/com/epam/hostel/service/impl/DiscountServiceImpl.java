package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.dao.DiscountDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.DiscountService;
import com.epam.hostel.service.exception.ServiceException;

import java.util.List;

/**
 * Created by ASUS on 18.11.2016.
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public void addDiscount(int clientId, int value, int administratorId) throws ServiceException {
        if(!Validator.validateInt(clientId) || !Validator.validateInt(value) || !Validator.validateInt(administratorId)){
            throw new ServiceException("Wrong parameters for adding discount");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        DiscountDAO discountDAO = factory.getDiscountDAO();

        try{
            Discount discount = new Discount();
            discount.setClientId(clientId);
            discount.setValue(value);
            discount.setAdministratorId(administratorId);

            discountDAO.insert(discount);
        } catch (DAOException e){
            throw new ServiceException("Service layer: cannot add a discount", e);
        }
    }

    @Override
    public List<Discount> getAllDiscountsByClientId(int id) throws ServiceException {
        if(!Validator.validateInt(id)){
            throw new ServiceException("Wrong parameters for getting all discounts by client id");
        }
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        DiscountDAO dao = factory.getDiscountDAO();
        List<Discount> discounts = null;

        try{
            discounts = dao.findByClientId(id);
            return discounts;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all rooms", e);
        }
    }
}
