package com.mparker.playlytics.exception;

// Imports

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)

public class CustomAccessDeniedException extends RuntimeException {
    public CustomAccessDeniedException(String message) {

        super(message);

    }
}
