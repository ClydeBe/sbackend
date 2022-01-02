package com.thewheel.sawatu.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    @NotNull
    @Positive
    private Float price;
    private String label;
    private String image;
    private String usage;
    @PositiveOrZero
    private Integer quantity;
    @NotBlank
    private String description;
    private String characteristics;

    @NotNull
    @JsonProperty("vendorUsername")
    @JsonInclude(NON_NULL)
    private String vendorName;

    @JsonInclude(NON_NULL)
    private UserDto vendor;

    private LocalDateTime updatedAt;

}
