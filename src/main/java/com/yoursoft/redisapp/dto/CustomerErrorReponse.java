package com.yoursoft.redisapp.dto;

import lombok.Value;

@Value
public class CustomerErrorReponse {
    private String code;
    private String message;
}
