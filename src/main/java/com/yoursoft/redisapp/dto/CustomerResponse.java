package com.yoursoft.redisapp.dto;

import lombok.Data;
import lombok.Value;

@Data
public class CustomerResponse {

    private String id;
    private String name;
    private String lastName;
}
