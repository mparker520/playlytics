package com.mparker.playlytics.exception;

// Imports

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)

public class ExistingResourceException extends RuntimeException {
    public ExistingResourceException(String message) {

        super(message);

    }
}
