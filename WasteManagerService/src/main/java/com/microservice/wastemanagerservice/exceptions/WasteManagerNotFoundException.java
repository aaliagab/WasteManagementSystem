package com.microservice.wastemanagerservice.exceptions;

public class WasteManagerNotFoundException extends Exception{
    public WasteManagerNotFoundException(String message) {
        super(message);
    }
}
