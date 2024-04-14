package com.microservice.wastemanageraddressservice.exception;

public class WasteManagerRepeatedException extends Exception{
    public WasteManagerRepeatedException(String message) {
        super(message);
    }
}
