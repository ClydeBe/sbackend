package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.PostService;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.PostDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.thewheel.sawatu.constants.ApiDocumentationConstants.*;
import static com.thewheel.sawatu.constants.ApiEndpointsConstants.*;
import static com.thewheel.sawatu.constants.SecurityConstants.SECURITY_NAME;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @ApiOperation(value = POST_GET_ALL,
            notes = POST_GET_ALL_NOTE,
            response = PageDto.class)
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PageDto.class)
    @GetMapping(ENDPOINT_POST_GET_ALL)
    public PageDto<PostDto> getAll(@PathVariable("username")String username,
                                   @RequestParam("page") Optional<Integer> p,
                                   @RequestParam("size") Optional<Integer> s) {
        int page = p.orElse(0);
        int size = s.orElse(5);
        return postService.listUserPost(PageRequest.of(page, size), username);
    }



    @ApiOperation(value = POST_GET_LAST,
            notes = POST_GET_LAST_NOTE,
            response = PostDto[].class)
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PostDto[].class)
    @GetMapping(ENDPOINT_POST_GET_LAST_POST)
    public List<PostDto> getLastPost() {
        return postService.getLastPosts();
    }



    @ApiOperation(value = POST_GET,
            notes = POST_GET_NOTE,
            response = PostDto.class)
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PostDto.class)
    @GetMapping(ENDPOINT_POST_GET)
    public PostDto get(@PathVariable("id") Long id) {
        return postService.get(id);
    }



    @ApiOperation(value = POST_CREATE,
            notes = POST_CREATE_NOTE,
            response = PostDto.class)
    @Operation(
            method = POST,
            description = POST_CREATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_CREATED,
            message = MESSAGE_CREATED,
            response = PostDto.class)
    @PostMapping(ENDPOINT_POST_CREATE)
    public PostDto create(@Valid @RequestBody PostDto postDto) {
        return postService.save(postDto);
    }



    @ApiOperation(value = POST_UPDATE,
            notes = POST_UPDATE_NOTE,
            response = PostDto.class)
    @Operation(
            method = PUT,
            description = POST_UPDATE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK,
            response = PostDto.class)
    @PutMapping(ENDPOINT_POST_UPDATE)
    public PostDto update(@Valid @RequestBody PostDto postDto) {
        return postService.save(postDto);
    }



    @ApiOperation(value = POST_DELETE)
    @Operation(
            method = DELETE,
            description = POST_DELETE,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = CODE_OK,
            message = MESSAGE_OK)
    @DeleteMapping(ENDPOINT_POST_DELETE)
    public void delete(@RequestBody PostDto productDto) {
        postService.delete(productDto);
    }
}
