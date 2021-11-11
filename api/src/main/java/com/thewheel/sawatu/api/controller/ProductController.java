package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.exception.BadRequestException;
import com.thewheel.sawatu.core.service.interfaces.ProductService;
import com.thewheel.sawatu.database.repository.ProductSearchRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProductDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.thewheel.sawatu.shared.constant.ApiDocumentationConstants.*;
import static com.thewheel.sawatu.shared.constant.ApiEndpointsConstants.*;
import static com.thewheel.sawatu.shared.constant.MessageConstants.PRODUCT_QUERY_EXCEPTION;
import static com.thewheel.sawatu.shared.constant.SecurityConstants.SECURITY_NAME;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductSearchRepository searchRepository;



    @ApiOperation(value = PRODUCT_GET_ALL,
            notes = PRODUCT_GET_ALL_NOTE,
            response = PageDto.class)
    @Operation(
            method = GET,
            description = PRODUCT_GET_ALL,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PageDto.class)
    @GetMapping(ENDPOINT_PRODUCT_GET_ALL)
    public PageDto<ProductDto> getAll(@RequestParam("page") Optional<Integer> p,
                                      @RequestParam("size") Optional<Integer> s) {
        int page = p.orElse(0);
        int size = s.orElse(20);
        return productService.list(PageRequest.of(page, size));
    }



    @ApiOperation(value = PRODUCT_QUERY,
            notes = PRODUCT_QUERY_NOTE,
            response = ProductDto[].class)
    @Operation(
            method = GET,
            description = PRODUCT_QUERY,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = ProductDto[].class)
    @GetMapping(ENDPOINT_PRODUCT_QUERY)
    public List<ProductDto> query(@RequestParam("query") Optional<String> query,
                                  @RequestParam("min") Optional<Integer> min,
                                  @RequestParam("max") Optional<Integer> max,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        boolean areRangeParamsPresents = min.isPresent() && max.isPresent();
        if(query.isEmpty() && !areRangeParamsPresents)
            throw new BadRequestException(PRODUCT_QUERY_EXCEPTION);
        if(query.isPresent() && areRangeParamsPresents)
            return productService.findByQuery(
                    query.get(),
                    min.get(),
                    max.get(),
                    page.orElse(0),
                    size.orElse(25));
        if(query.isPresent() && !areRangeParamsPresents)
            return productService.findByQuery(
                    query.get(),
                    page.orElse(0),
                    size.orElse(25));
        return productService.findByQuery(
                min.get(),
                max.get(),
                page.orElse(0),
                size.orElse(25));
    }



    @ApiOperation(value = PRODUCT_GET,
            notes = PRODUCT_GET_NOTE,
            response = ProductDto.class)
    @Operation(
            method = GET,
            description = PRODUCT_GET,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = ProductDto.class)
    @GetMapping(ENDPOINT_PRODUCT_GET)
    public ProductDto get(@PathVariable("id") Long id) {
        return productService.get(id);
    }



    @ApiOperation(value = PRODUCT_CREATE,
            notes = PRODUCT_CREATE_NOTE,
            response = ProductDto.class)
    @Operation(
            method = POST,
            description = PRODUCT_CREATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_CREATED,
            message = MESSAGE_CREATED,
            response = ProductDto.class)
    @PreAuthorize("hasRole('VENDOR') && #productDto.vendorName == authentication.name")
    @PostMapping(ENDPOINT_PRODUCT_CREATE)
    public ProductDto create(@Valid @RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }



    @ApiOperation(value = PRODUCT_UPDATE,
            notes = PRODUCT_UPDATE_NOTE,
            response = ProductDto.class)
    @Operation(
            method = PUT,
            description = PRODUCT_UPDATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = ProductDto.class)
    @PreAuthorize("hasRole('VENDOR') && #productDto.vendorName == authentication.name")
    @PutMapping(ENDPOINT_HAIRCUT_UPDATE)
    public ProductDto update(@Valid @RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }



    @ApiOperation(value = PRODUCT_DELETE)
    @Operation(
            method = DELETE,
            description = PRODUCT_DELETE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK)
    @PreAuthorize("hasRole('VENDOR') && #productDto.vendorName == authentication.name")
    @DeleteMapping(ENDPOINT_POST_DELETE)
    public void delete(@RequestBody ProductDto productDto) {
        productService.delete(productDto);
    }
}
