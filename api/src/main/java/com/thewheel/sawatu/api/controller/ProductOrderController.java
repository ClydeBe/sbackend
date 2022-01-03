package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.ProductOrderService;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProductOrderDto;
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
public class ProductOrderController {

    private final ProductOrderService productOrderService;


    @ApiOperation(value = PRODUCT_ORDER_GET_ALL,
            notes = PRODUCT_ORDER_GET_ALL_NOTE,
            response = PageDto.class)
    @Operation(
            method = GET,
            description = PRODUCT_ORDER_GET_ALL,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PageDto.class)
    @PreAuthorize("#username == authentication.name")
    @GetMapping(ENDPOINT_PRODUCT_ORDER_GET_ALL)
    public PageDto<ProductOrderDto> getAll(@PathVariable("username")String username,
                                           @RequestParam("page") Optional<Integer> p,
                                           @RequestParam("size") Optional<Integer> s) {
        int page = p.orElse(0);
        int size = s.orElse(20);
        return productOrderService.list(PageRequest.of(page, size), username);
    }



    @ApiOperation(value = PRODUCT_ORDER_GET,
            notes = PRODUCT_ORDER_GET_NOTE,
            response = ProductOrderDto.class)
    @Operation(
            method = GET,
            description = PRODUCT_ORDER_GET,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = ProductOrderDto.class)
    @PostAuthorize("returnObject.userName == authentication.name")
    @GetMapping(ENDPOINT_PRODUCT_ORDER_GET)
    public ProductOrderDto get(@PathVariable("id") Long id) {
        return productOrderService.get(id);
    }



    @ApiOperation(value = PRODUCT_ORDER_CREATE,
            notes = PRODUCT_ORDER_CREATE_NOTE,
            response = ProductOrderDto.class)
    @Operation(
            method = POST,
            description = PRODUCT_ORDER_CREATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_CREATED,
            message = MESSAGE_CREATED,
            response = ProductOrderDto.class)
    @PreAuthorize("#productOrderDto.userName == authentication.name")
    @PostMapping(ENDPOINT_PRODUCT_ORDER_CREATE)
    public ProductOrderDto create(@Valid @RequestBody ProductOrderDto productOrderDto) {
        return productOrderService.save(productOrderDto);
    }



    @ApiOperation(value = PRODUCT_ORDER_UPDATE,
            notes = PRODUCT_ORDER_UPDATE_NOTE,
            response = ProductOrderDto.class)
    @Operation(
            method = PUT,
            description = PRODUCT_ORDER_UPDATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = ProductOrderDto.class)
    @PreAuthorize("#productOrderDto.userName == authentication.name")
    @PutMapping(ENDPOINT_PRODUCT_ORDER_UPDATE)
    @ResponseBody
    public ProductOrderDto update(@Valid @RequestBody ProductOrderDto productOrderDto) {
        return productOrderService.save(productOrderDto);
    }



    @ApiOperation(value = PRODUCT_ORDER_DELETE)
    @Operation(
            method = GET,
            description = PRODUCT_ORDER_DELETE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK)
    @PreAuthorize("#productOrderDto.userName == authentication.name")
    @DeleteMapping(ENDPOINT_PRODUCT_ORDER_DELETE)
    @ResponseBody
    public void delete(@RequestBody ProductOrderDto productOrderDto) {
        productOrderService.delete(productOrderDto);
    }
}
