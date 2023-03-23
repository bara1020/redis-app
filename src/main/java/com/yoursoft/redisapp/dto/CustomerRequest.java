package com.yoursoft.redisapp.dto;

import lombok.Data;

@Data
public class CustomerRequest {

    private String id;
    private String name;
    private String lastName;
    private String phoneNumber;
}
