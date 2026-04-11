package com.kevin.zenvy.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GeneralException extends RuntimeException {
    private final HttpStatus statusCode;

    public GeneralException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
