package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.CommentService;
import com.thewheel.sawatu.database.model.Comment;
import com.thewheel.sawatu.database.model.Post;
import com.thewheel.sawatu.database.repository.CommentRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.shared.dto.CommentDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final Mapper mapper;
    private final CommentRepository commentRepository;

    @Override
    public PageDto<CommentDto> getPostComments(Pageable pageable, Long postId) {
        Page<Comment> comments = commentRepository.findCommentsByPostIdOrderByIdDesc(postId, pageable);
        return PageDto.<CommentDto>builder()
                .items(comments.stream()
                         .map(mapper::fromEntity)
                         .collect(Collectors.toList()))
                .pages(comments.getTotalPages())
                .build();
    }

    @Override
    public CommentDto get(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                                                             MessageConstants.COMMENT,
                                                                             id)));
        return mapper.fromEntity(comment);
    }

    @Override
    public CommentDto save(CommentDto commentDto, Long postId) {
        Comment comment = mapper.toEntity(commentDto);
        comment.setPost(Post.builder().id(postId).build());
        comment = commentRepository.save(comment);
        return mapper.fromEntity(comment);
    }

    @Override
    public void delete(CommentDto commentDto) {
        commentRepository.deleteById(commentDto.getId());
    }
}
