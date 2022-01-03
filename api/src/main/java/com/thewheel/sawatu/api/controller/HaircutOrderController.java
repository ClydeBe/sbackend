package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.HaircutOrderService;
import com.thewheel.sawatu.shared.dto.HaircutOrderDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.thewheel.sawatu.constants.ApiDocumentationConstants.*;
import static com.thewheel.sawatu.constants.ApiEndpointsConstants.*;
import static com.thewheel.sawatu.constants.SecurityConstants.SECURITY_NAME;

@RestController
@RequiredArgsConstructor
public class HaircutOrderController {

    private final HaircutOrderService haircutOrderService;



    @ApiOperation(value = HAIRCUT_ORDER_GET_ALL,
            notes = HAIRCUT_ORDER_GET_ALL_NOTE,
            response = PageDto.class)
    @Operation(
            method = GET,
            description = HAIRCUT_ORDER_GET_ALL,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PageDto.class)
    @PreAuthorize("#username == authentication.name")
    @GetMapping(ENDPOINT_HAIRCUT_ORDER_GET_ALL)
    public PageDto<HaircutOrderDto> getAll(@PathVariable("username") String username,
                                           @RequestParam("page") Optional<Integer> p,
                                           @RequestParam("size") Optional<Integer> s) {
        int page = p.orElse(0);
        int size = s.orElse(20);
        return haircutOrderService.list(PageRequest.of(page, size), username);
    }



    @ApiOperation(value = HAIRCUT_ORDER_GET,
            notes = HAIRCUT_ORDER_GET_NOTE,
            response = HaircutOrderDto.class)
    @Operation(
            method = GET,
            description = HAIRCUT_ORDER_GET,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = HaircutOrderDto.class)
    @PostAuthorize("#returnObject.clientName == authentication.name")
    @GetMapping(ENDPOINT_HAIRCUT_ORDER_GET)
    public HaircutOrderDto get(@PathVariable("id") Long id) {
        return haircutOrderService.get(id);
    }



    @ApiOperation(value = HAIRCUT_ORDER_CREATE,
            notes = HAIRCUT_ORDER_CREATE_NOTE,
            response = HaircutOrderDto.class)
    @Operation(
            method = POST,
            description = HAIRCUT_ORDER_CREATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_CREATED,
            message = MESSAGE_CREATED,
            response = HaircutOrderDto.class)
    @PreAuthorize("#haircutOrderDto.clientName == authentication.name")
    @PostMapping(ENDPOINT_HAIRCUT_ORDER_CREATE)
    public HaircutOrderDto create(@Valid @RequestBody HaircutOrderDto haircutOrderDto) {
        return haircutOrderService.save(haircutOrderDto);
    }



    @ApiOperation(value = HAIRCUT_ORDER_UPDATE,
            notes = HAIRCUT_ORDER_UPDATE_NOTE,
            response = HaircutOrderDto.class)
    @Operation(
            method = PUT,
            description = HAIRCUT_ORDER_UPDATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = HaircutOrderDto.class)
    @PreAuthorize("#haircutOrderDto.clientName == authentication.name")
    @PutMapping(ENDPOINT_HAIRCUT_ORDER_UPDATE)
    public HaircutOrderDto update(@Valid @RequestBody HaircutOrderDto haircutOrderDto) {
        return haircutOrderService.save(haircutOrderDto);
    }



    @ApiOperation(value = HAIRCUT_ORDER_DELETE)
    @Operation(
            method = DELETE,
            description = HAIRCUT_ORDER_DELETE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK)
    @PreAuthorize("#haircutOrderDto.clientName == authentication.name")
    @DeleteMapping(ENDPOINT_HAIRCUT_ORDER_DELETE)
    public void delete(@RequestBody HaircutOrderDto haircutOrderDto) {
        haircutOrderService.delete(haircutOrderDto);
    }
}
