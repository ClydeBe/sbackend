package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.database.model.Comment;
import com.thewheel.sawatu.database.repository.CommentRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.CommentDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private Mapper mapper;

    @Mock
    private CommentRepository commentRepository;

    @Test
    public void getPostComment_shouldReturn() {
        // Given
        Comment entity_1 = Comment.builder()
                .id(TestConstants.ID_1)
                .build();
        Comment entity_2 = Comment.builder()
                .id(TestConstants.ID_2)
                .build();
        CommentDto dto_1 = CommentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        CommentDto dto_2 = CommentDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<Comment> orders = new PageImpl<Comment>(Arrays.asList(entity_1, entity_2));
        PageDto<CommentDto> expected = PageDto.<CommentDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        given(commentRepository.findCommentsByPostIdOrderByIdDesc(TestConstants.ID_1, PAGEABLE)).willReturn(
                orders);
        given(mapper.fromEntity(entity_1)).willReturn(dto_1);
        given(mapper.fromEntity(entity_2)).willReturn(dto_2);

        // When
        PageDto<CommentDto> response = commentService.getPostComments(PAGEABLE, TestConstants.ID_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(entity_1);
        verify(mapper, times(1)).fromEntity(entity_2);
        verify(mapper, times(2)).fromEntity(any(Comment.class));

        verify(commentRepository, times(1)).findCommentsByPostIdOrderByIdDesc(TestConstants.ID_1,
                                                                           PAGEABLE);
        verify(commentRepository, times(1)).findCommentsByPostIdOrderByIdDesc(any(), any(Pageable.class));
    }

    @Test
    public void get_shouldReturn() {
        // Given
        Comment entity = Comment.builder()
                .id(TestConstants.ID_1)
                .build();
        CommentDto dto = CommentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(commentRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Comment> of(entity));
        given(mapper.fromEntity(entity)).willReturn(dto);


        // When
        CommentDto response = commentService.get(TestConstants.ID_1);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(dto);

        verify(commentRepository, times(1)).findById(TestConstants.ID_1);
        verify(commentRepository, times(1)).findById(any());

        verify(mapper, times(1)).fromEntity(entity);
        verify(mapper, times(1)).fromEntity(any(Comment.class));
    }

    @Test
    public void get_shouldThrow() {
        // Given

        given(commentRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Comment> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> commentService.get(TestConstants.ID_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.COMMENT,
                                               TestConstants.ID_1));

        verify(commentRepository, times(1)).findById(TestConstants.ID_1);
        verify(commentRepository, times(1)).findById(any());
    }

    @Test
    public void save_shouldReturn() {
        // Given
        Comment entity = Comment.builder()
                .id(TestConstants.ID_1)
                .build();
        CommentDto dto = CommentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(commentRepository.save(entity)).willReturn(entity);
        given(mapper.fromEntity(entity)).willReturn(dto);
        given(mapper.toEntity(dto)).willReturn(entity);


        // When
        CommentDto response = commentService.save(dto, TestConstants.ID_1);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(dto);

        verify(commentRepository, times(1)).save(entity);
        verify(commentRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(entity);
        verify(mapper, times(1)).fromEntity(any(Comment.class));

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(CommentDto.class));
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        CommentDto dto = CommentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(commentRepository).deleteById(TestConstants.ID_1);

        // When
        commentService.delete(dto);

        // Then
        verify(commentRepository, times(1)).deleteById(TestConstants.ID_1);
        verify(commentRepository, times(1)).deleteById(any());
    }

}