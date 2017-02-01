package com.epam.hostel.service.exception;

/**
 * Exception which show that room number is invalid.
 */
public class ServiceExistingRoomNumberException extends ServiceException {
    public ServiceExistingRoomNumberException(){
        super();
    }

    public ServiceExistingRoomNumberException(String message){
        super(message);
    }

    public ServiceExistingRoomNumberException(Exception e){
        super(e);
    }

    public ServiceExistingRoomNumberException(String message, Exception e){
        super(message, e);
    }
}
