package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.PostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PageDto<PostDto> list(Pageable page);
    PageDto<PostDto> listUserPost(Pageable page, String username);
    List<PostDto> getLastPosts();
    PostDto get(Long id);
    PostDto save(PostDto postDto);
    void delete(PostDto postDto);
}
