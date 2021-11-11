package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.database.model.Role;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.thewheel.sawatu.shared.constant.ApiDocumentationConstants.*;
import static com.thewheel.sawatu.shared.constant.ApiEndpointsConstants.*;
import static com.thewheel.sawatu.shared.constant.SecurityConstants.SECURITY_NAME;

@RestController
public class RoleController {

    @ApiOperation(value = ROLE_GET,
            notes = ROLE_GET_NOTE,
            response = Role[].class)
    @Operation(
            method = GET,
            description = ROLE_GET,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = Role[].class)
    @GetMapping(ENDPOINT_ROLE_GET_ALL)
    public Role[] getAll() {
        return Role.values();
    }
}
