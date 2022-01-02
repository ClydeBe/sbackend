package com.thewheel.sawatu.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {

    private Long id;

    @Valid
    @NotNull
    @NotEmpty
    private List<PeriodDto> availabilities;

    @NotBlank
    @JsonInclude(NON_NULL)
    @JsonProperty("username")
    private String userName;

    @JsonInclude(NON_NULL)
    private UserDto user;

    private LocalDateTime updatedAt;

}
