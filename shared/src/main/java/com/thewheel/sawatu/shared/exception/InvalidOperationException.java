package com.thewheel.sawatu.shared.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidOperationException extends RuntimeException {

    private final String message;
    private final Throwable cause;

    public InvalidOperationException(String message) {
        this.message = message;
        this.cause = null;
    }

    public InvalidOperationException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }
}
