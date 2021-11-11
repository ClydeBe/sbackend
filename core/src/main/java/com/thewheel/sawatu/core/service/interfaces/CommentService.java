package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.CommentDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentService {
    PageDto<CommentDto> getPostComments(Pageable pageable, Long postId);
    CommentDto get(Long id);
    CommentDto save(CommentDto commentDto, Long postId);
    void delete(CommentDto commentDto);
}
