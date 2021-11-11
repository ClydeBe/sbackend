package com.thewheel.sawatu.shared.dto;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Value
@Builder
@FieldDefaults(level = PROTECTED, makeFinal = true)
public class PageDto<T> {

    List<T> items;

    int pages;

}
