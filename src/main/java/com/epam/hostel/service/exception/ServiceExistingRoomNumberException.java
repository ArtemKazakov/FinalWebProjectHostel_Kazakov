package com.epam.hostel.service.exception;

/**
 * Created by ASUS on 07.01.2017.
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
