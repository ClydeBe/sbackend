package com.thewheel.sawatu.shared.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thewheel.sawatu.database.model.Address;
import com.thewheel.sawatu.database.model.Role;
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
public class  UserDto {

    private Address address;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    private String photo;
    private String status;
    private String lastname;
    private String firstname;

    @JsonInclude(NON_NULL)
    private String password;

    private Boolean isActive;

    private Role role;

    private LocalDateTime updatedAt;
}
