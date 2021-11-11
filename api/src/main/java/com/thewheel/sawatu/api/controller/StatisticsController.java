package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.StatisticsService;
import com.thewheel.sawatu.shared.dto.StatisticsDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.thewheel.sawatu.shared.constant.ApiDocumentationConstants.*;
import static com.thewheel.sawatu.shared.constant.ApiEndpointsConstants.*;
import static com.thewheel.sawatu.shared.constant.SecurityConstants.SECURITY_NAME;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @ApiOperation(value = STATISTICS_GET,
            notes = STATISTICS_GET_NOTE,
            response = StatisticsDto.class)
    @Operation(
            method = GET,
            description = STATISTICS_GET,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = StatisticsDto.class)
    @GetMapping(ENDPOINT_STATISTICS_GET)
    public StatisticsDto get(@PathVariable("username") String username) {
        return statisticsService.get(username);
    }

}
