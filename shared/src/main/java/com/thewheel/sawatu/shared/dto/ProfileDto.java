package com.thewheel.sawatu.shared.dto;


import com.thewheel.sawatu.shared.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private int haircutCount;

    private int followersCount;

    private int rate;

    private String userName;

    private UserDto user;

    private LocalDateTime updatedAt;

}
