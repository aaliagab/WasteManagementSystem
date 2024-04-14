package com.microservice.wastemanagerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WasteManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WasteManagerServiceApplication.class, args);
    }

}
