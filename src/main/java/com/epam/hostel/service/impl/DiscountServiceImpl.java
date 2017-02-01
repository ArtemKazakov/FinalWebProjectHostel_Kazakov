package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.dao.DiscountDAO;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.DiscountService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.util.Validator;

import java.util.List;
import java.util.Optional;

/**
 * Provides a business-logic with the {@link Discount} entity and relate with it.
 */
public class DiscountServiceImpl extends Service implements DiscountService {
    private final DiscountDAO discountDAO = DAOFactory.getInstance().getDiscountDAO();

    /**
     * Creates a discount in a data source
     *
     * @param clientId        an id of client who got this discount
     * @param value           a value of discount
     * @param administratorId an id of administrator who gave this discount
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void addDiscount(int clientId, int value, int administratorId) throws ServiceException {
        if(!Validator.validateInt(clientId) ||
                !Validator.validateDiscountValue(value) ||
                !Validator.validateInt(administratorId)){
            throw new ServiceException("Wrong parameters for adding discount");
        }

        Discount discount = new Discount();
        discount.setClientId(clientId);
        discount.setValue(value);
        discount.setAdministratorId(administratorId);

        this.service("Service layer: cannot add a discount",
                () -> {
                    discountDAO.insert(discount);
                    return Optional.empty();
                }
        );
    }

    /**
     * Deletes a discount from a data source by id
     *
     * @param id an id of discount for deleting
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void deleteDiscount(int id) throws ServiceException {
        if(!Validator.validateInt(id)){
            throw new ServiceException("Wrong parameters for deleting discount");
        }

        this.service("Service layer: cannot delete a discount",
                () -> {
                    discountDAO.delete(id);
                    return Optional.empty();
                }
        );
    }

    /**
     * Updates a discount in a data source
     *
     * @param discountId an id of discount for updating
     * @param clientId   an id of client who got this discount
     * @param value      a value of discount
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void updateDiscount(int discountId, int clientId, int value) throws ServiceException {
        if(!Validator.validateInt(discountId) ||
                !Validator.validateInt(clientId) ||
                !Validator.validateDiscountValue(value)){
            throw new ServiceException("Wrong parameters for updating discount");
        }

        Discount discount = new Discount();
        discount.setId(discountId);
        discount.setClientId(clientId);
        discount.setValue(value);

        this.service("Service layer: cannot update a discount",
                () -> {
                    discountDAO.updateValue(discount);
                    return Optional.empty();
                }
        );
    }

    /**
     * Return all discounts from a data source by client id
     * @param id an id of client that received these discounts
     * @return a {@link List} of discounts
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public List<Discount> getAllDiscountsByClientId(int id) throws ServiceException {
        if(!Validator.validateInt(id)){
            throw new ServiceException("Wrong parameters for getting all discounts by client id");
        }

        return this.service("Service layer: cannot get all discounts by client id",
                () ->  discountDAO.findByClientId(id)
        );
    }

    /**
     * Return all unused discounts from a data source by client id
     * @param id an id of client that received these discounts
     * @return a {@link List} of discounts
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public List<Discount> getAllUnusedDiscountsByClientId(int id) throws ServiceException {
        if(!Validator.validateInt(id)){
            throw new ServiceException("Wrong parameters for getting all unused discounts by client id");
        }

        return this.service("Service layer: cannot get all unused discounts by client id",
                () ->  discountDAO.findUnusedByClientId(id)
        );
    }
}
