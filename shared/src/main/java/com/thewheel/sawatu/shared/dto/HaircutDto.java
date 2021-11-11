package com.thewheel.sawatu.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HaircutDto {


    private Long id;
    @NotBlank
    private Float price;
    @NotBlank
    private String label;
    // Duration in minutes
    @NotBlank
    private Long duration;
    @NotBlank
    private Float vatRatio;
    @NotBlank
    private String photo;

    @NotBlank
    @JsonInclude(NON_NULL)
    @JsonProperty("vendorUsername")
    private String vendorName;

    @JsonInclude(NON_NULL)
    private UserDto vendor;

    private LocalDateTime updatedAt;

}
