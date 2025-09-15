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

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<ExceptionDetailDTO> handleCustomAccessDenied(AccessDeniedException ex) {

        ExceptionDetailDTO exceptionDetail = new ExceptionDetailDTO("FORBIDDEN", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionDetail);

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetailDTO> handleNotFound(NotFoundException ex) {

        ExceptionDetailDTO exceptionDetail = new ExceptionDetailDTO("NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDetail);

    }

    @ExceptionHandler(ExistingResourceException.class)
    public ResponseEntity<ExceptionDetailDTO> handleExistingResourceException(ExistingResourceException ex) {

        ExceptionDetailDTO exceptionDetail = new ExceptionDetailDTO("RESOURCE_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionDetail);

    }

    @ExceptionHandler(SessionParticipantTeamMismatchException.class)
    public ResponseEntity<ExceptionDetailDTO> handleSessionParticipantTeamMismatchException(SessionParticipantTeamMismatchException ex) {

        ExceptionDetailDTO exceptionDetail = new ExceptionDetailDTO("SESSION_PARTICIPANTS_TEAM_MISMATCH", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDetail);

    }

}
