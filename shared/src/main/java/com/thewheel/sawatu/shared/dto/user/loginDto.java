package com.thewheel.sawatu.shared.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class loginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
