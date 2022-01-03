package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.ReviewService;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ReviewDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.thewheel.sawatu.constants.ApiDocumentationConstants.*;
import static com.thewheel.sawatu.constants.ApiEndpointsConstants.*;
import static com.thewheel.sawatu.constants.SecurityConstants.SECURITY_NAME;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = REVIEW_GET_ALL,
            notes = REVIEW_GET_ALL_NOTE,
            response = PageDto.class)
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PageDto.class)
    @GetMapping(ENDPOINT_REVIEW_GET_ALL)
    public PageDto<ReviewDto> getAll(@PathVariable("username")String username,
                                     @RequestParam("page") Optional<Integer> p,
                                     @RequestParam("size") Optional<Integer> s) {
        int page = p.orElse(0);
        int size = s.orElse(20);
        return reviewService.list(PageRequest.of(page, size), username);
    }


    @ApiOperation(value = REVIEW_GET,
            notes = REVIEW_GET_NOTE,
            response = ReviewDto.class)
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = ReviewDto.class)
    @GetMapping(ENDPOINT_REVIEW_GET)
    public ReviewDto get(@PathVariable("id") Long id) {
        return reviewService.get(id);
    }


    @ApiOperation(value = REVIEW_CREATE,
            notes = REVIEW_CREATE_NOTE,
            response = ReviewDto.class)
    @Operation(
            method = POST,
            description = REVIEW_CREATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_CREATED,
            message = MESSAGE_CREATED,
            response = ReviewDto.class)
    @PreAuthorize("#reviewDto.reviewerName == authentication.name and hasRole('ROLE_USER')")
    @PostMapping(ENDPOINT_REVIEW_CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto create(@Valid @RequestBody ReviewDto reviewDto) {
        return reviewService.save(reviewDto);
    }



    @ApiOperation(value = REVIEW_UPDATE,
            notes = REVIEW_UPDATE_NOTE,
             response = ReviewDto.class)
    @Operation(
            method = PUT,
            description = REVIEW_UPDATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = ReviewDto.class)
    @PreAuthorize("#reviewDto.reviewerName == authentication.name and hasRole('ROLE_USER')")
    @PutMapping(ENDPOINT_REVIEW_UPDATE)
    public ReviewDto update(@Valid @RequestBody ReviewDto reviewDto) {
        return reviewService.save(reviewDto);
    }



    @ApiOperation(value = REVIEW_DELETE)
    @Operation(
            method = DELETE,
            description = REVIEW_DELETE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK)
    @PreAuthorize("#reviewDto.reviewerName == authentication.name and hasRole('ROLE_USER')")
    @DeleteMapping(ENDPOINT_REVIEW_DELETE)
    public void delete(@RequestBody ReviewDto reviewDto) {
        reviewService.delete(reviewDto);
    }
}
