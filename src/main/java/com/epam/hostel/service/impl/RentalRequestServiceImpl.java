package com.epam.hostel.service.impl;

import com.epam.hostel.bean.entity.Discount;
import com.epam.hostel.bean.entity.RentalRequest;
import com.epam.hostel.bean.entity.Room;
import com.epam.hostel.bean.entity.ScheduleRecord;
import com.epam.hostel.dao.*;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.dao.factory.DAOFactory;
import com.epam.hostel.service.RentalRequestService;
import com.epam.hostel.service.exception.ServiceException;
import com.epam.hostel.service.util.Validator;

import java.util.List;

/**
 * Created by ASUS on 04.12.2016.
 */
public class RentalRequestServiceImpl implements RentalRequestService {
    @Override
    public void makeRentalRequest(ScheduleRecord scheduleRecord, RentalRequest rentalRequest, int discountId) throws ServiceException {
        if (!Validator.validateInt(scheduleRecord.getRoomNumber()) || !Validator.validateDate(scheduleRecord.getCheckInDate()) ||
                !Validator.validateDate(scheduleRecord.getCheckoutDate()) || Validator.validateInt(rentalRequest.getClient().getId()) ||
                !Validator.validateDaysStayNumber(rentalRequest.getDaysStayNumber()) ||
                !Validator.validateSeatsNumber(rentalRequest.getSeatsNumber()) ||
                !Validator.validateDate(rentalRequest.getCheckInDate()) ||
                (rentalRequest.isFullPayment()) ?
                !Validator.validateInt(rentalRequest.getPayment()) : !Validator.validateInt(scheduleRecord.getPaymentDuty())) {
            throw new ServiceException("Wrong parameters for making rental request");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        ScheduleRecordDAO scheduleRecordDAO = factory.getScheduleRecordDAO();
        RequestDAO requestDAO = factory.getRequestDAO();
        DiscountDAO discountDAO = factory.getDiscountDAO();
        RoomDAO roomDAO = factory.getRoomDAO();

        try {
            Discount discount = discountDAO.findById(discountId);
            if (!Validator.validateDiscount(discount, rentalRequest.getClient().getId())) {
                throw new ServiceException("Wrong parameters for making rental request");
            }

            Room room = roomDAO.findByNumber(scheduleRecord.getRoomNumber());
            if (!Validator.validateRoom(room, rentalRequest.getSeatsNumber())) {
                throw new ServiceException("Wrong parameters for making rental request");
            }

            for (ScheduleRecord sRec : scheduleRecordDAO.findByDateInterval(scheduleRecord.getCheckInDate(), scheduleRecord.getCheckoutDate())) {
                if (sRec.getRoomNumber() == room.getNumber()) {
                    throw new ServiceException("Wrong parameters for making rental request");
                }
            }

            if (!Validator.validatePayment(room, discount, rentalRequest, scheduleRecord)) {
                throw new ServiceException("Wrong parameters for making rental request");
            }

            int requestId = requestDAO.insert(rentalRequest);

            scheduleRecord.setRequestId(requestId);
            scheduleRecordDAO.insert(scheduleRecord);

            if (discount != null) {
                discount.setUsed(true);
                discountDAO.update(discount);
            }

        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot add a rental request", e);
        }
    }

    @Override
    public void updateRentalRequest(int id, boolean accepted, int administratorId) throws ServiceException {
        if (!Validator.validateInt(id) || !Validator.validateInt(administratorId)) {
            throw new ServiceException("Wrong parameter for updating rental request");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RequestDAO requestDAO = factory.getRequestDAO();
        ScheduleRecordDAO scheduleRecordDAO = factory.getScheduleRecordDAO();

        try {
            RentalRequest rentalRequest = requestDAO.findById(id);

            if ((rentalRequest == null) || (rentalRequest.getAccepted() != null)){
                throw new ServiceException("Try to update rental request second time");
            }

            rentalRequest.setAccepted(accepted);
            rentalRequest.getAdministrator().setId(administratorId);
            requestDAO.update(rentalRequest);

            if (!accepted) {
                scheduleRecordDAO.deleteByRentalRequestId(id);
            }
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot update request", e);
        }
    }

    @Override
    public void deleteRentalRequest(int id) throws ServiceException {
        if (!Validator.validateInt(id)) {
            throw new ServiceException("Wrong parameters for deleting request");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RequestDAO requestDAO = factory.getRequestDAO();

        try {
            requestDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete a request", e);
        }
    }

    @Override
    public RentalRequest getRentalRequestById(int id) throws ServiceException {
        if (!Validator.validateInt(id)) {
            throw new ServiceException("Wrong parameters for deleting request");
        }

        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RequestDAO requestDAO = factory.getRequestDAO();
        UserDAO userDAO = factory.getUserDAO();
        RentalRequest rentalRequest = null;

        try {
            rentalRequest = requestDAO.findById(id);

            rentalRequest.setClient(userDAO.findByIdAndRole(rentalRequest.getClient().getId(), false));
            if (rentalRequest.getAdministrator().getId() != 0) {
                rentalRequest.setAdministrator(userDAO.findByIdAndRole(rentalRequest.getAdministrator().getId(), true));
            } else {
                rentalRequest.setAdministrator(null);
            }

            return rentalRequest;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get rental request by id", e);
        }
    }

    @Override
    public List<RentalRequest> getAllRentalRequests() throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RequestDAO requestDAO = factory.getRequestDAO();
        UserDAO userDAO = factory.getUserDAO();
        List<RentalRequest> rentalRequests = null;

        try {
            rentalRequests = requestDAO.findAll();

            for (RentalRequest rentalRequest : rentalRequests) {
                rentalRequest.setClient(userDAO.findByIdAndRole(rentalRequest.getClient().getId(), false));
                if (rentalRequest.getAdministrator().getId() != 0) {
                    rentalRequest.setAdministrator(userDAO.findByIdAndRole(rentalRequest.getAdministrator().getId(), true));
                } else {
                    rentalRequest.setAdministrator(null);
                }
            }

            return rentalRequests;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all rooms", e);
        }
    }

    @Override
    public List<RentalRequest> getAllRentalRequestsByClientId(int id) throws ServiceException {
        if (!Validator.validateInt(id)) {
            throw new ServiceException("Wrong parameters for getting all discounts by client id");
        }
        DAOFactory factory = DAOFactory.getInstance(DAOFactory.Factories.MYSQL);
        RequestDAO dao = factory.getRequestDAO();
        List<RentalRequest> rentalRequests = null;

        try {
            rentalRequests = dao.findByClientId(id);
            return rentalRequests;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all rooms", e);
        }
    }
}
