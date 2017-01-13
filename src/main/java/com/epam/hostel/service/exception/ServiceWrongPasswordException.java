package com.epam.hostel.service.exception;


/**
 * Created by ASUS on 29.10.2016.
 */
public class ServiceWrongPasswordException extends ServiceException {
    public ServiceWrongPasswordException(){
        super();
    }

    public ServiceWrongPasswordException(String message){
        super(message);
    }

    public ServiceWrongPasswordException(Exception e){
        super(e);
    }

    public ServiceWrongPasswordException(String message, Exception e){
        super(message, e);
    }
}
