package com.thewheel.sawatu.api.exception;

import com.thewheel.sawatu.shared.exception.BadRequestException;
import com.thewheel.sawatu.shared.exception.InvalidOperationException;
import com.thewheel.sawatu.shared.exception.ServerException;
import org.hibernate.TransientPropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class AppRestControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public AppError handleEntityNotFoundException(EntityNotFoundException exception) {
        final HttpStatus notFound = NOT_FOUND;
        return AppError.builder()
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public AppError handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        final HttpStatus serverError = INTERNAL_SERVER_ERROR;
        return AppError.builder()
                .httpCode(serverError.value())
                .message(exception.getMessage())
                .cause(exception.getMostSpecificCause().toString())
                .build();
    }

    @ExceptionHandler(ServerException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public AppError handleServerException(ServerException exception) {
        final HttpStatus serverError = INTERNAL_SERVER_ERROR;
        return AppError.builder()
                .httpCode(serverError.value())
                .message(exception.getMessage())
                .cause(exception.getMessage())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(FORBIDDEN)
    public AppError handleAccessDeniedException(AccessDeniedException exception) {
        final HttpStatus forbidden = FORBIDDEN;
        return AppError.builder()
                .httpCode(forbidden.value())
                .message(exception.getMessage())
                .cause("The user does not have authorities to access the requested resource")
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public AppError handleBadRequestException(BadRequestException exception) {
        final HttpStatus badRequest = BAD_REQUEST;
        return AppError.builder()
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .cause("Cannot find GET parameters")
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public AppError handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        final HttpStatus badRequest = BAD_REQUEST;
        return AppError.builder()
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(BAD_REQUEST)
    public AppError handleInvalidOperationException(InvalidOperationException exception) {
        final HttpStatus badRequest = BAD_REQUEST;
        return AppError.builder()
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(TransientPropertyValueException.class)
    @ResponseStatus(BAD_REQUEST)
    public AppError handleTransientPropertyValueException(TransientPropertyValueException exception) {
        final HttpStatus badRequest = BAD_REQUEST;
        return AppError.builder()
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .cause("The provided entity is not transient")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public AppError handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final HttpStatus beanNotValid = BAD_REQUEST;
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
