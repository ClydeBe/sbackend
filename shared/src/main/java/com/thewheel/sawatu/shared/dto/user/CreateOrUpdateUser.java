package com.thewheel.sawatu.shared.dto.user;

import com.thewheel.sawatu.database.model.Role;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class CreateOrUpdateUser {

    private final Role role;
    private final boolean isActive;

    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;

}
