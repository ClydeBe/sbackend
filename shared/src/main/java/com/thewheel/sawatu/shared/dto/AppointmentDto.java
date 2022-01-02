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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDto {

    private Long id;
    @DateTimeValid
    private LocalDateTime dateTime;

    @NotNull
    private HaircutDto haircut;
    @NotNull
    private HaircutOrderDto haircutOrder;

    @NotBlank
    private boolean isVendorAddress;
    @NotBlank
    @JsonInclude(NON_NULL)
    @JsonProperty("clientUsername")
    private String clientName;
    @NotBlank
    @JsonInclude(NON_NULL)
    @JsonProperty("vendorUsername")
    private String vendorName;

    @JsonInclude(NON_NULL)
    private UserDto client;
    @JsonInclude(NON_NULL)
    private UserDto vendor;

    private LocalDateTime updatedAt;

}
