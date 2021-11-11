package com.thewheel.sawatu.core.mailing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class Email {

    private String to;
    private String from;
    private String subject;
    private String content;
    private Map< String, Object > model;

}
