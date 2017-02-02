package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.dao.*;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.dao.transaction.TransactionManager;
import com.epam.hostel.dao.transaction.impl.TransactionManagerImpl;
import com.epam.hostel.service.RentalRequestService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.util.Security;
import com.epam.hostel.service.util.Validator;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Provides a business-logic with the {@link RentalRequest} entity and relate with it.
 */
public class RentalRequestServiceImpl extends Service implements RentalRequestService {
    private static final Logger logger = Logger.getLogger(RentalRequestServiceImpl.class);
    private static TransactionManager transactionManager = TransactionManagerImpl.getInstance();
    private final DiscountDAO discountDAO = DAOFactory.getInstance().getDiscountDAO();
    private final RoomDAO roomDAO = DAOFactory.getInstance().getRoomDAO();
    private final RequestDAO requestDAO = DAOFactory.getInstance().getRequestDAO();
    private final ScheduleRecordDAO scheduleRecordDAO = DAOFactory.getInstance().getScheduleRecordDAO();
    private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

    /**
     * Makes a new rental request and a schedule record in a data source
     *
     * @param scheduleRecord a {@link ScheduleRecord} object
     * @param rentalRequest  a {@link RentalRequest} object
     * @param discountId     an id of used discount
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void makeRentalRequest(ScheduleRecord scheduleRecord, RentalRequest rentalRequest, int discountId) throws ServiceException {
        if (!Validator.validateRentalRequest(scheduleRecord, rentalRequest)) {
            throw new ServiceException("Wrong parameters for making rental request");
        }

        try {
            transactionManager.doInTransaction(() -> {
                Discount discount = discountDAO.findById(discountId);
                if (!Security.checkDiscount(discount, rentalRequest.getClient().getId())) {
                    throw new ServiceException("Wrong parameters for making rental request");
                }

                Room room = roomDAO.findByNumber(scheduleRecord.getRoomNumber());
                if (!Security.checkRoom(room, rentalRequest.getSeatsNumber())) {
                    throw new ServiceException("Wrong parameters for making rental request");
                }

                for (ScheduleRecord sRec : scheduleRecordDAO.findByDateInterval(scheduleRecord.getCheckInDate(), scheduleRecord.getCheckoutDate())) {
                    if (sRec.getRoomNumber() == room.getNumber()) {
                        throw new ServiceException("Wrong parameters for making rental request");
                    }
                }

                if (!Security.checkPayment(room, discount, rentalRequest, scheduleRecord)) {
                    throw new ServiceException("Wrong parameters for making rental request");
                }

                int requestId = requestDAO.insert(rentalRequest);

                scheduleRecord.setRequestId(requestId);
                scheduleRecordDAO.insert(scheduleRecord);

                if (discount != null) {
                    discount.setUsed(true);
                    discountDAO.update(discount);
                }

                return Optional.empty();
            });

        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot add a rental request", e);
        }
    }

    /**
     * Updates a rental request in a data source
     *
     * @param id              an id of updating rental request
     * @param accepted        an accept status of request
     * @param administratorId an administrator id who update state of request
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void updateRentalRequest(int id, boolean accepted, int administratorId) throws ServiceException {
        if (!Validator.validateInt(id) || !Validator.validateInt(administratorId)) {
            throw new ServiceException("Wrong parameter for updating rental request");
        }

        try {
            transactionManager.doInTransaction(() -> {
                RentalRequest rentalRequest = requestDAO.findById(id);

                if ((rentalRequest == null) || (rentalRequest.getAccepted() != null)) {
                    throw new ServiceException("Try to update rental request second time");
                }

                rentalRequest.setAccepted(accepted);
                rentalRequest.getAdministrator().setId(administratorId);
                requestDAO.update(rentalRequest);

                if (!accepted) {
                    scheduleRecordDAO.deleteByRentalRequestId(id);
                }

                return Optional.empty();
            }
            );
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot update request", e);
        }
    }

    /**
     * Deletes a rental request from a data source by id
     *
     * @param id an id of rental request for deleting
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public void deleteRentalRequest(int id) throws ServiceException {
        if (!Validator.validateInt(id)) {
            throw new ServiceException("Wrong parameters for deleting request");
        }

        this.service("Service layer: cannot delete a request",
                () -> {
                    requestDAO.delete(id);
                    return Optional.empty();
                }
        );
    }

    /**
     * Return a rental request from a data source by id
     *
     * @param id an id of rental request
     * @return a rental request
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public RentalRequest getRentalRequestById(int id) throws ServiceException {
        if (!Validator.validateInt(id)) {
            throw new ServiceException("Wrong parameters for deleting request");
        }

        try {
            return transactionManager.doInTransaction(() -> {
                RentalRequest rentalRequest = requestDAO.findById(id);

                rentalRequest.setClient(userDAO.findByIdAndRole(rentalRequest.getClient().getId(), false));
                if (rentalRequest.getAdministrator().getId() != 0) {
                    rentalRequest.setAdministrator(userDAO.findByIdAndRole(rentalRequest.getAdministrator().getId(), true));
                } else {
                    rentalRequest.setAdministrator(null);
                }

                return rentalRequest;
            });
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot get rental request by id", e);
        }
    }

    /**
     * Return all rental requests from a data source
     *
     * @return a {@link List} of rental requests
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public List<RentalRequest> getAllRentalRequests() throws ServiceException {
        try {
            return transactionManager.doInTransaction(() -> {
                List<RentalRequest> rentalRequests = requestDAO.findAll();

                for (RentalRequest rentalRequest : rentalRequests) {
                    rentalRequest.setClient(userDAO.findByIdAndRole(rentalRequest.getClient().getId(), false));
                    if (rentalRequest.getAdministrator().getId() != 0) {
                        rentalRequest.setAdministrator(userDAO.findByIdAndRole(rentalRequest.getAdministrator().getId(), true));
                    } else {
                        rentalRequest.setAdministrator(null);
                    }
                }

                return rentalRequests;
            });
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot get all requests", e);
        }
    }

    /**
     * Return all rental requests from a data source
     *
     * @param start       the number from which accounts will be returned
     * @param amount      of rental requests
     * @return a {@link List} of rental requests
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public List<RentalRequest> getAllRentalRequestsLimited(int start, int amount) throws ServiceException {
        try {
            return transactionManager.doInTransaction(() -> {
                List<RentalRequest> rentalRequests = requestDAO.findAllLimited(start, amount);

                for (RentalRequest rentalRequest : rentalRequests) {
                    rentalRequest.setClient(userDAO.findByIdAndRole(rentalRequest.getClient().getId(), false));
                    if (rentalRequest.getAdministrator().getId() != 0) {
                        rentalRequest.setAdministrator(userDAO.findByIdAndRole(rentalRequest.getAdministrator().getId(), true));
                    } else {
                        rentalRequest.setAdministrator(null);
                    }
                }

                return rentalRequests;
            });
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Service layer: cannot get all requests limited", e);
        }
    }

    /**
     * Return all rental requests from a data source by client id
     *
     * @param id an id of client that make rental requests
     * @return a {@link List} of discounts
     * @throws ServiceException in case of error occurred with a data source
     *                          or validation of data
     */
    @Override
    public List<RentalRequest> getAllRentalRequestsByClientId(int id) throws ServiceException {
        if (!Validator.validateInt(id)) {
            throw new ServiceException("Wrong parameters for getting all discounts by client id");
        }

        return this.service("Service layer: cannot get all requests by client id",
                () ->  requestDAO.findByClientId(id)
        );
    }

    /**
     * Returns number of rental requests in data source.
     *
     * @return amount of rental requests
     * @throws ServiceException if error occurred with data source
     */
    @Override
    public int getRentalRequestsCount() throws ServiceException {
        return this.service("Service layer: cannot get count of rental requests",
                requestDAO::selectRequestCount
        );
    }
}
