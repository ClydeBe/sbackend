package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.PostService;
import com.thewheel.sawatu.database.model.Post;
import com.thewheel.sawatu.database.repository.PostRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.PostDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final Mapper mapper;
    private final PostRepository postRepository;

    @Override
    public PageDto<PostDto> list(Pageable page) {
        Page<Post> posts = postRepository.findAll(page);
        return PageDto.<PostDto>builder()
                .items(posts.stream()
                         .map(mapper::fromEntity)
                         .collect(Collectors.toList()))
                .pages(posts.getTotalPages())
                .build();
    }

    @Override
    public List<PostDto> getLastPosts() {
        return postRepository.findTop3IdByOrderByIdDesc()
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public PageDto<PostDto> listUserPost(Pageable page, String username) {
        Page<Post> posts = postRepository.findAllByAuthorNameOrderByIdDesc(username, page);
        return PageDto.<PostDto>builder()
                .items(posts.stream()
                                  .map(mapper::fromEntity)
                                  .collect(Collectors.toList()))
                .pages(posts.getTotalPages())
                .build();
    }

    @Override
    public PostDto get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                                                             MessageConstants.POST,
                                                                             id)));
        return mapper.fromEntity(post);
    }

    @Override
    public PostDto save(PostDto postDto) {
        Post post = mapper.toEntity(postDto);
        post = postRepository.save(post);
        return mapper.fromEntity(post);
    }

    @Override
    public void delete(PostDto postDto) {
        postRepository.deleteById(postDto.getId());
    }
}
