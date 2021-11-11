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
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    @JsonInclude(NON_NULL)
    private Long replyId;
    @NotBlank
    private Long postId;
    @NotBlank
    private String body;
    @NotBlank
    @JsonInclude(NON_NULL)
    @JsonProperty("authorUsername")
    private String authorName;
    @JsonInclude(NON_NULL)
    private List<CommentDto> comments;

    @JsonInclude(NON_NULL)
    private UserDto author;

    private LocalDateTime updatedAt;

}
