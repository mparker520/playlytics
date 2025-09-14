package com.mparker.playlytics.exception;

// Imports
import com.mparker.playlytics.dto.ExceptionDetailDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDetailDTO> handleAccessDenied(AccessDeniedException ex) {

            ExceptionDetailDTO exceptionDetail = new ExceptionDetailDTO("FORBIDDEN", "You Do Not Have Access to This Resource");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionDetail);

    }

}
