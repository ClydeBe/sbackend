package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.AppointmentService;
import com.thewheel.sawatu.shared.dto.AppointmentDto;
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

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @ApiOperation(value = APPOINTMENT_GET_ALL,
            notes = APPOINTMENT_GET_ALL_NOTE,
            response = PageDto.class)
    @Operation(
            method = GET,
            description = APPOINTMENT_GET_ALL,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PageDto.class)
    @PreAuthorize("#username == authentication.name")
    @PostFilter("filterObject.clientName == authentication.name")
    @GetMapping(ENDPOINT_APPOINTMENT_GET_ALL)
    public PageDto<AppointmentDto> getAll(@PathVariable("username") String username,
                                          @RequestParam("page") Optional<Integer> p,
                                          @RequestParam("size") Optional<Integer> s) {
        int page = p.orElse(0);
        int size = s.orElse(20);
        return appointmentService.listAllByClientName(PageRequest.of(page, size), username);
    }



    @ApiOperation(value = APPOINTMENT_GET,
            notes = APPOINTMENT_GET_NOTE,
            response = AppointmentDto.class)
    @Operation(
            method = GET,
            description = APPOINTMENT_GET,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = AppointmentDto.class)
    @PostAuthorize("returnObject.get().clientName == authentication.name")
    @GetMapping(ENDPOINT_APPOINTMENT_GET)
    public AppointmentDto get(@PathVariable("id") Long id) {
        return appointmentService.get(id);
    }



    @ApiOperation(value = APPOINTMENT_CREATE,
            notes = APPOINTMENT_CREATE_NOTE,
            response = AppointmentDto.class)
    @Operation(
            method = POST,
            description = APPOINTMENT_CREATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_CREATED,
            message = MESSAGE_CREATED,
            response = AppointmentDto.class)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(ENDPOINT_APPOINTMENT_CREATE)
    public AppointmentDto create(@Valid @RequestBody AppointmentDto appointmentDto) {
        return appointmentService.save(appointmentDto);
    }

    // TODO change to requestUpdate and validateUpdateRequest

//    @ApiOperation(value = "U^date user's appointment",
//            notes = "Return updated appointment",
//            response = AppointmentDto.class)
//    @Operation(
//            method = GET,
//            description = APPOINTMENT_UPDATE,
//            security = @SecurityRequirement(name = SECURITY_NAME)
//    )
//    @ApiResponse(code = 200, message = "Ok",
//            response = AppointmentDto.class)
//    @PreAuthorize("#appointmentDto.clientName == authentication.name")
//    @PutMapping("/appointment")
//    public Optional<AppointmentDto> update(@Valid @RequestBody AppointmentDto appointmentDto) {
//        return appointmentService.save(appointmentDto);
//    }


    @ApiOperation(value = APPOINTMENT_DELETE)
    @Operation(
            method = GET,
            description = APPOINTMENT_DELETE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK)
    @PreAuthorize("#appointmentDto.clientName == authentication.name OR " +
                  "#appointmentDto.vendorName == authentication.name")
    @DeleteMapping(ENDPOINT_APPOINTMENT_DELETE)
    public void delete(@RequestBody AppointmentDto appointmentDto) {
        appointmentService.delete(appointmentDto);
    }
}
