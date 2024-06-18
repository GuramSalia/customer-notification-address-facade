package com.crocobet.customer_notification_address_facade.config;

import com.crocobet.customer_notification_address_facade.exceptions.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Not found")})
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            WebRequest request
    ) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Not found")})
    public ResponseEntity<ErrorDetails> handleCustomerNotFoundException(
            CustomerNotFoundException ex,
            WebRequest request
    ) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Not found")})
    public ResponseEntity<ErrorDetails> handleAddressNotFoundException(
            AddressNotFoundException ex,
            WebRequest request
    ) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(NotificationPreferencesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Not found")})
    public ResponseEntity<ErrorDetails> handleNotificationPreferencesNotFoundException(
            NotificationPreferencesNotFoundException ex,
            WebRequest request
    ) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Not found")})
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(
            UserNotFoundException ex,
            WebRequest request
    ) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public final ResponseEntity<ErrorDetails> handleAllException(
            Exception ex, WebRequest request
    ) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
}
