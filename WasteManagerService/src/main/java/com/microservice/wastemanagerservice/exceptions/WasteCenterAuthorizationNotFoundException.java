package com.microservice.wastemanagerservice.exceptions;

public class WasteCenterAuthorizationNotFoundException extends Exception{
    public WasteCenterAuthorizationNotFoundException(String message) {
        super(message);
    }
}
