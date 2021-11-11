package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.PostService;
import com.thewheel.sawatu.shared.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.PostDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

    @InjectMocks
    private PostController controller;

    @Mock
    private PostService service;

    @Test
    public void getAll_shouldReturn() {
        // Given
        PostDto dto_1 = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        PostDto dto_2 = PostDto.builder()
                .id(TestConstants.ID_2)
                .build();
        PageDto<PostDto> expected = PageDto.<PostDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(service.listUserPost(PAGEABLE, TestConstants.USERNAME_1)).willReturn(expected);

        // When
        PageDto<PostDto> response = controller.getAll(TestConstants.USERNAME_1, Optional.of(0), Optional.of(10));

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).listUserPost(PAGEABLE, TestConstants.USERNAME_1);
        verify(service, times(1)).listUserPost(any(), any());

    }

    @Test
    public void getLastPost_shouldReturn() {
        // Given
        PostDto dto_1 = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        PostDto dto_2 = PostDto.builder()
                .id(TestConstants.ID_2)
                .build();
        List<PostDto> expected = Arrays.asList(dto_1, dto_2);
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(service.getLastPosts()).willReturn(expected);

        // When
        List<PostDto> response = controller.getLastPost();

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).getLastPosts();
        verify(service, times(1)).getLastPosts();

    }

    @Test
    public void get_shouldReturn() {
        // Given
        PostDto expected = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.get(TestConstants.ID_1)).willReturn(expected);

        // When
        PostDto response = controller.get(TestConstants.ID_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).get(TestConstants.ID_1);
        verify(service, times(1)).get(any());
    }

    @Test
    public void create_shouldReturn() {
        // Given
        PostDto expected = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.save(expected)).willReturn(expected);

        // When
        PostDto response = controller.create(expected);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).save(expected);
        verify(service, times(1)).save(any());
    }

    @Test
    public void update_shouldReturn() {
        // Given
        PostDto expected = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.save(expected)).willReturn(expected);

        // When
        PostDto response = controller.update(expected);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).save(expected);
        verify(service, times(1)).save(any());
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        PostDto expected = PostDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(service).delete(expected);

        // When
        controller.delete(expected);

        // Then
        verify(service, times(1)).delete(expected);
        verify(service, times(1)).delete(any());
    }

}