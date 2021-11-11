package com.thewheel.sawatu.api.exception;

import com.thewheel.sawatu.core.exception.BadRequestException;
import com.thewheel.sawatu.core.exception.InvalidOperationException;
import org.hibernate.TransientPropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppRestControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public AppError handleEntityNotFoundException(EntityNotFoundException exception) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        return AppError.builder()
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public AppError handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        final HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;
        return AppError.builder()
                .httpCode(serverError.value())
                .message(exception.getMessage())
                .cause(exception.getMostSpecificCause().toString())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public AppError handleAccessDeniedException(AccessDeniedException exception) {
        final HttpStatus forbidden = HttpStatus.FORBIDDEN;
        return AppError.builder()
                .httpCode(forbidden.value())
                .message(exception.getMessage())
                .cause("The user does not have authorities to access the requested resource")
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    public AppError handleBadRequestException(BadRequestException exception) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return AppError.builder()
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .cause("Cannot find GET parameters")
                .build();
    }

    @ExceptionHandler(InvalidOperationException.class)
    public AppError handleInvalidOperationException(InvalidOperationException exception) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return AppError.builder()
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(TransientPropertyValueException.class)
    public AppError handleTransientPropertyValueException(TransientPropertyValueException exception) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return AppError.builder()
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .cause("The provided entity is not transient")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AppError handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final HttpStatus beanNotValid = HttpStatus.BAD_REQUEST;
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((beanError) -> {
            String fieldName = ((FieldError) beanError).getField();
            String errorMessage = beanError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return AppError.builder()
                .httpCode(beanNotValid.value())
                .message(exception.getMessage())
                .cause("Cannot find GET parameters")
                .errors(errors)
                .build();
    }

}
