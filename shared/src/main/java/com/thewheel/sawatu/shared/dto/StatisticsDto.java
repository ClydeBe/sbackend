package com.thewheel.sawatu.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDto {

    private Long id;
    private int rate;
    private int haircutCount;
    private int followersCount;
    @JsonInclude(NON_NULL)
    @JsonProperty("userUsername")
    private String userName;

    @JsonInclude(NON_NULL)
    private UserDto user;

    private LocalDateTime updatedAt;

}
