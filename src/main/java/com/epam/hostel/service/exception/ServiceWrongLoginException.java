package com.epam.hostel.service.exception;


/**
 * Created by ASUS on 29.10.2016.
 */
public class ServiceWrongLoginException extends ServiceException {
    public ServiceWrongLoginException(){
        super();
    }

    public ServiceWrongLoginException(String message){
        super(message);
    }

    public ServiceWrongLoginException(Exception e){
        super(e);
    }

    public ServiceWrongLoginException(String message, Exception e){
        super(message, e);
    }
}
