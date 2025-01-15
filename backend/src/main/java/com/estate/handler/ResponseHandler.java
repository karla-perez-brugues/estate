package com.estate.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String name, HttpStatus status, Object responseObject) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(name, responseObject);

        return new ResponseEntity<Object>(map, status);
    }
}
