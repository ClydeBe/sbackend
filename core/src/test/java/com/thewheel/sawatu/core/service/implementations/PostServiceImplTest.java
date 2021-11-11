package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.database.model.Post;
import com.thewheel.sawatu.database.repository.PostRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.PostDto;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private Mapper mapper;

    @Mock
    private PostRepository postRepository;

    @Test
    public void list_shouldReturn() {
        // Given
        Post entity_1 = Post.builder()
                .id(TestConstants.ID_1)
                .build();
        Post entity_2 = Post.builder()
                .id(TestConstants.ID_2)
                .build();
        PostDto dto_1 = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        PostDto dto_2 = PostDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<Post> posts = new PageImpl<Post>(Arrays.asList(entity_1, entity_2));
        PageDto<PostDto> expected = PageDto.<PostDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        given(postRepository.findAll(PAGEABLE)).willReturn(posts);
        given(mapper.fromEntity(entity_1)).willReturn(dto_1);
        given(mapper.fromEntity(entity_2)).willReturn(dto_2);

        // When
        PageDto<PostDto> response = postService.list(PAGEABLE);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(entity_1);
        verify(mapper, times(1)).fromEntity(entity_2);
        verify(mapper, times(2)).fromEntity(any(Post.class));

        verify(postRepository, times(1)).findAll(PAGEABLE);
        verify(postRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void getLastPost_shouldReturn() {
        // Given
        Post entity_1 = Post.builder()
                .id(TestConstants.ID_1)
                .build();
        Post entity_2 = Post.builder()
                .id(TestConstants.ID_2)
                .build();
        PostDto dto_1 = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        PostDto dto_2 = PostDto.builder()
                .id(TestConstants.ID_2)
                .build();
        List<Post> posts = Arrays.asList(entity_1, entity_2);
        List<PostDto> expected = Arrays.asList(dto_1, dto_2);
        given(postRepository.findTop3IdByOrderByIdDesc()).willReturn(posts);
        given(mapper.fromEntity(entity_1)).willReturn(dto_1);
        given(mapper.fromEntity(entity_2)).willReturn(dto_2);

        // When
        List<PostDto> response = postService.getLastPosts();

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(entity_1);
        verify(mapper, times(1)).fromEntity(entity_2);
        verify(mapper, times(2)).fromEntity(any(Post.class));

        verify(postRepository, times(1)).findTop3IdByOrderByIdDesc();
        verify(postRepository, times(1)).findTop3IdByOrderByIdDesc();
    }

    @Test
    public void listUserPost_shouldReturn() {
        // Given
        Post entity_1 = Post.builder()
                .id(TestConstants.ID_1)
                .build();
        Post entity_2 = Post.builder()
                .id(TestConstants.ID_2)
                .build();
        PostDto dto_1 = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        PostDto dto_2 = PostDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<Post> posts = new PageImpl<>(Arrays.asList(entity_1, entity_2));
        PageDto<PostDto> expected = PageDto.<PostDto>builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        given(postRepository.findAllByAuthorNameOrderByIdDesc(TestConstants.USERNAME_1, PAGEABLE)).willReturn(posts);
        given(mapper.fromEntity(entity_1)).willReturn(dto_1);
        given(mapper.fromEntity(entity_2)).willReturn(dto_2);

        // When
        PageDto<PostDto> response = postService.listUserPost(PAGEABLE, TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(entity_1);
        verify(mapper, times(1)).fromEntity(entity_2);
        verify(mapper, times(2)).fromEntity(any(Post.class));

        verify(postRepository, times(1)).findAllByAuthorNameOrderByIdDesc(TestConstants.USERNAME_1, PAGEABLE);
        verify(postRepository, times(1)).findAllByAuthorNameOrderByIdDesc(any(), any());
    }

    @Test
    public void get_shouldReturn() {
        // Given
        Post entity = Post.builder()
                .id(TestConstants.ID_1)
                .build();
        PostDto dto = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(postRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Post> of(entity));
        given(mapper.fromEntity(entity)).willReturn(dto);


        // When
        PostDto response = postService.get(TestConstants.ID_1);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(dto);

        verify(postRepository, times(1)).findById(TestConstants.ID_1);
        verify(postRepository, times(1)).findById(any());

        verify(mapper, times(1)).fromEntity(entity);
        verify(mapper, times(1)).fromEntity(any(Post.class));
    }

    @Test
    public void get_shouldThrow() {
        // Given

        given(postRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Post> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> postService.get(TestConstants.ID_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.POST,
                                               TestConstants.ID_1));

        verify(postRepository, times(1)).findById(TestConstants.ID_1);
        verify(postRepository, times(1)).findById(any());
    }

    @Test
    public void save_shouldReturn() {
        // Given
        Post entity = Post.builder()
                .id(TestConstants.ID_1)
                .build();
        PostDto dto = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(postRepository.save(entity)).willReturn(entity);
        given(mapper.fromEntity(entity)).willReturn(dto);
        given(mapper.toEntity(dto)).willReturn(entity);


        // When
        PostDto response = postService.save(dto);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(dto);

        verify(postRepository, times(1)).save(entity);
        verify(postRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(entity);
        verify(mapper, times(1)).fromEntity(any(Post.class));

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(PostDto.class));
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        PostDto dto = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(postRepository).deleteById(TestConstants.ID_1);

        // When
        postService.delete(dto);

        // Then
        verify(postRepository, times(1)).deleteById(TestConstants.ID_1);
        verify(postRepository, times(1)).deleteById(any());
    }

}