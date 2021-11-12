package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.StatisticsService;
import com.thewheel.sawatu.shared.dto.StatisticsDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.thewheel.sawatu.shared.constant.ApiDocumentationConstants.STATISTICS_GET;
import static com.thewheel.sawatu.shared.constant.ApiDocumentationConstants.STATISTICS_GET_NOTE;
import static com.thewheel.sawatu.shared.constant.ApiEndpointsConstants.*;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @ApiOperation(value = STATISTICS_GET,
            notes = STATISTICS_GET_NOTE,
            response = StatisticsDto.class)
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = StatisticsDto.class)
    @GetMapping(ENDPOINT_STATISTICS_GET)
    public StatisticsDto get(@PathVariable("username") String username) {
        return statisticsService.get(username);
    }

}
