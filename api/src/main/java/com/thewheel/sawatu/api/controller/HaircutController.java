package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.HaircutService;
import com.thewheel.sawatu.shared.dto.HaircutDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.thewheel.sawatu.constants.ApiDocumentationConstants.*;
import static com.thewheel.sawatu.constants.ApiEndpointsConstants.*;
import static com.thewheel.sawatu.constants.SecurityConstants.SECURITY_NAME;

@RestController
@RequiredArgsConstructor
public class HaircutController {

    private final HaircutService haircutService;



    @ApiOperation(value = HAIRCUT_GET_ALL,
            notes = HAIRCUT_GET_ALL_NOTE,
            response = PageDto.class)
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PageDto.class)
    @GetMapping(ENDPOINT_HAIRCUT_GET_ALL)
    public PageDto<HaircutDto> getAll(@RequestParam("page") Optional<Integer> p,
                                      @RequestParam("size") Optional<Integer> s) {
        int page = p.orElse(0);
        int size = s.orElse(20);
        return haircutService.list(PageRequest.of(page, size));
    }



    @ApiOperation(value = HAIRCUT_GET,
            notes = HAIRCUT_GET_NOTE,
            response = HaircutDto.class)
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = HaircutDto.class)
    @GetMapping(ENDPOINT_HAIRCUT_GET)
    public HaircutDto get(@PathVariable("id") Long id) {
        return haircutService.get(id);
    }



    @ApiOperation(value = HAIRCUT_CREATE,
            notes = HAIRCUT_CREATE_NOTE,
            response = HaircutDto.class)
    @Operation(
            method = POST,
            description = HAIRCUT_CREATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_CREATED,
            message = MESSAGE_CREATED,
            response = HaircutDto.class)
    @PostMapping(ENDPOINT_HAIRCUT_CREATE)
    public HaircutDto create(@Valid @RequestBody HaircutDto haircutDto) {
        return haircutService.save(haircutDto);
    }



    @ApiOperation(value = HAIRCUT_UPDATE,
            notes = HAIRCUT_UPDATE_NOTE,
            response = HaircutDto.class)
    @Operation(
            method = PUT,
            description = HAIRCUT_UPDATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = HaircutDto.class)
    @PutMapping(ENDPOINT_HAIRCUT_UPDATE)
    public HaircutDto update(@Valid @RequestBody HaircutDto haircutDto) {
        return haircutService.save(haircutDto);
    }



    @ApiOperation(value = HAIRCUT_DELETE)
    @Operation(
            method = DELETE,
            description = HAIRCUT_DELETE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK)
    @DeleteMapping(ENDPOINT_HAIRCUT_DELETE)
    @ResponseBody
    public void delete(@RequestBody HaircutDto haircutDto) {
        haircutService.delete(haircutDto);
    }
}
