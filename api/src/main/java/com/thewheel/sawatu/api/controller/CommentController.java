package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.CommentService;
import com.thewheel.sawatu.shared.dto.CommentDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.thewheel.sawatu.shared.constant.ApiDocumentationConstants.*;
import static com.thewheel.sawatu.shared.constant.ApiEndpointsConstants.*;
import static com.thewheel.sawatu.shared.constant.SecurityConstants.SECURITY_NAME;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = COMMENT_GET_ALL,
            notes = COMMENT_GET_ALL_NOTE,
            response = PageDto.class)
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PageDto.class)
    @GetMapping(ENDPOINT_COMMENT_GET_ALL)
    @ResponseBody
    public PageDto<CommentDto> getAll(@PathVariable("postId")Long postId, @RequestParam("page") Optional<Integer> p,
                                      @RequestParam("size") Optional<Integer> s) {
        return commentService.getPostComments(PageRequest.of(p.orElse(0), s.orElse(10)), postId);
    }



    @ApiOperation(value = COMMENT_GET,
            notes = COMMENT_GET_NOTE,
            response = CommentDto.class)
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = CommentDto.class)
    @GetMapping(ENDPOINT_COMMENT_GET)
    public CommentDto get(@PathVariable("id") Long id) {
        return commentService.get(id);
    }


    @ApiOperation(value = COMMENT_CREATE,
            notes = COMMENT_CREATE_NOTE,
            response = CommentDto.class)
    @Operation(
            method = POST,
            description = COMMENT_CREATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_CREATED,
            message = MESSAGE_CREATED,
            response = CommentDto.class)
    @PostMapping(ENDPOINT_COMMENT_CREATE)
    @PreAuthorize("#commentDto.authorName == authentication.name")
    public CommentDto create(@Valid @RequestBody CommentDto commentDto, @PathVariable("postId") Long postId) {
        return commentService.save(commentDto, postId);
    }


    @ApiOperation(value = COMMENT_UPDATE,
            notes = COMMENT_UPDATE_NOTE,
            response = CommentDto.class)
    @Operation(
            method = PUT,
            description = COMMENT_UPDATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = CommentDto.class)
    @ResponseBody
    @PutMapping(ENDPOINT_COMMENT_UPDATE)
    @PreAuthorize("#commentDto.authorName == authentication.name")
    public CommentDto update(@Valid @RequestBody CommentDto commentDto, @PathVariable("postId") Long postId) {
        return commentService.save(commentDto, postId);
    }



    @ApiOperation(value = COMMENT_DELETE)
    @Operation(
            method = DELETE,
            description = COMMENT_DELETE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK)
    @DeleteMapping(ENDPOINT_COMMENT_DELETE)
    public void delete(@RequestBody CommentDto commentDto) {
        commentService.delete(commentDto);
    }
}
