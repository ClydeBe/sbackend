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
public class PostDto {

    private Long id;
    @NotBlank
    private String cover;
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    @NotBlank
    private String tags;

    @NotBlank
    @JsonProperty("authorUsername")
    private String authorName;

    @JsonInclude(NON_NULL)
    private UserDto author;

    private LocalDateTime updatedAt;

}
