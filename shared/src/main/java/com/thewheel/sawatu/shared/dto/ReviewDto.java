package com.thewheel.sawatu.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long id;
    @NotBlank @Min(0) @Max(5)
    private Integer rating;
    @NotBlank
    private String comment;

    @NotNull
    @JsonInclude(NON_NULL)
    @JsonProperty("reviewerUsername")
    private String reviewerName;
    @NotNull
    @JsonInclude(NON_NULL)
    @JsonProperty("selfUsername")
    private String selfName;

    @JsonInclude(NON_NULL)
    private UserDto reviewer;
    @JsonInclude(NON_NULL)
    private UserDto self;

    private LocalDateTime updatedAt;

}
