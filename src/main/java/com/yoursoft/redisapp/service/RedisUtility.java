package com.yoursoft.redisapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoursoft.redisapp.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUtility {

    Logger logger = LoggerFactory.getLogger(RedisUtility.class);

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;



    public void setValue(final String key, Customer customer)
            throws JsonProcessingException {
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(customer));
        redisTemplate.expire(key, 10, TimeUnit.MINUTES);
    }

    public Customer getValue(final String key)
            throws JsonProcessingException {

        String content = redisTemplate.opsForValue().get(key);

        if(content != null){
            logger.info("Customer fetched from cache");
            return objectMapper.readValue(content,Customer.class);
        }

        return null;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
