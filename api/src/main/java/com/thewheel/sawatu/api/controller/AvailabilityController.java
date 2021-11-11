package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.AvailabilityService;
import com.thewheel.sawatu.shared.dto.AvailabilityDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.thewheel.sawatu.shared.constant.ApiDocumentationConstants.*;
import static com.thewheel.sawatu.shared.constant.ApiEndpointsConstants.*;
import static com.thewheel.sawatu.shared.constant.SecurityConstants.SECURITY_NAME;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @ApiOperation(value = AVAILABILITY_GET_ALL,
            notes = AVAILABILITY_GET_ALL_NOTE,
            response = PageDto.class)
    @Operation(
            method = GET,
            description = AVAILABILITY_GET_ALL,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PageDto.class)
    @PreAuthorize("#username == authentication.name")
    @PostFilter("filterObject.userName == authentication.name")
    @GetMapping(ENDPOINT_AVAILABILITY_GET_ALL)
    public PageDto<AvailabilityDto> getAll(@PathVariable("username") String username,
                                           @RequestParam("page") Optional<Integer> p,
                                           @RequestParam("size") Optional<Integer> s) {
        int page = p.orElse(0);
        int size = s.orElse(20);
        return availabilityService.list(PageRequest.of(page, size), username);
    }



    @ApiOperation(value = "Get availability by id",
            notes = "Return an availability",
            response = AvailabilityDto.class)
    @Operation(
            method = GET,
            description = AVAILABILITY_GET,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = AvailabilityDto.class)
    @PostAuthorize("returnObject.get().userName == authentication.name")
    @GetMapping(ENDPOINT_AVAILABILITY_GET)
    public AvailabilityDto get(@PathVariable("id") Long id) {
        return availabilityService.get(id);
    }

    // TODO  Implements post hierachy and deal with all post requests



    @ApiOperation(value = AVAILABILITY_CREATE,
            notes = AVAILABILITY_CREATE_NOTE,
            response = AvailabilityDto.class)
    @Operation(
            method = POST,
            description = AVAILABILITY_CREATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_CREATED,
            message = MESSAGE_CREATED,
            response = AvailabilityDto.class)
    @ResponseStatus(CREATED)
    @PostMapping(ENDPOINT_AVAILABILITY_CREATE)
    public AvailabilityDto create(@Valid @RequestBody AvailabilityDto availabilityDto) {
        return availabilityService.save(availabilityDto);
    }



    @ApiOperation(value = AVAILABILITY_CREATE,
            notes = AVAILABILITY_UPDATE_NOTE,
            response = AvailabilityDto.class)
    @Operation(
            method = PUT,
            description = AVAILABILITY_UPDATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = AvailabilityDto.class)
    @PutMapping(ENDPOINT_AVAILABILITY_UPDATE)
    public AvailabilityDto update(@Valid @RequestBody AvailabilityDto availabilityDto) {
        return availabilityService.save(availabilityDto);
    }



    @ApiOperation(value = AVAILABILITY_DELETE)
    @Operation(
            method = DELETE,
            description = AVAILABILITY_DELETE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK)
    @PreAuthorize("#availabilityDto.userName == authentication.name")
    @DeleteMapping(ENDPOINT_AVAILABILITY_DELETE)
    @ResponseBody
    public void delete(@RequestBody AvailabilityDto availabilityDto) {
        availabilityService.delete(availabilityDto);
    }
}
