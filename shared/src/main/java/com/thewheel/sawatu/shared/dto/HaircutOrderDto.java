package com.thewheel.sawatu.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import com.thewheel.sawatu.shared.dto.validator.annotations.DateTimeValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HaircutOrderDto {

    private Long id;
    @NotBlank
    private String description;
    @Positive
    private Float price;

    @DateTimeValid
    private LocalDateTime date;

    @JsonInclude(NON_NULL)
    private UserDto client;
    @NotBlank
    @JsonProperty("clientUsername")
    @JsonInclude(NON_NULL)
    private String clientName;
    private HaircutDto haircut;
    private List<AppointmentDto> appointments;

    private LocalDateTime updatedAt;

}
