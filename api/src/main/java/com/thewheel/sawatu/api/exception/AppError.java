package com.thewheel.sawatu.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppError {
    private Integer httpCode;
    private String message;
    private String cause;
    private Map<String, String> errors;
}
