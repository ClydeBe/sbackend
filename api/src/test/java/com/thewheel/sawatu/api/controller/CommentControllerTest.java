package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.CommentService;
import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.shared.dto.CommentDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {

    @InjectMocks
    private CommentController controller;

    @Mock
    private CommentService service;

    @Test
    public void getAll_shouldReturn() {
        // Given
        CommentDto dto_1 = CommentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        CommentDto dto_2 = CommentDto.builder()
                .id(TestConstants.ID_2)
                .build();
        PageDto<CommentDto> expected = PageDto.<CommentDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(service.getPostComments(PAGEABLE, TestConstants.ID_1)).willReturn(expected);

        // When
        PageDto<CommentDto> response = controller.getAll(TestConstants.ID_1, Optional.of(0), Optional.of(10));

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).getPostComments(PAGEABLE, TestConstants.ID_1);
        verify(service, times(1)).getPostComments(any(), any());

    }

    @Test
    public void get_shouldReturn() {
        // Given
        CommentDto expected = CommentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.get(TestConstants.ID_1)).willReturn(expected);

        // When
        CommentDto response = controller.get(TestConstants.ID_1);

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
        CommentDto expected = CommentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.save(expected, TestConstants.ID_1)).willReturn(expected);

        // When
        CommentDto response = controller.create(expected, TestConstants.ID_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).save(expected, TestConstants.ID_1);
        verify(service, times(1)).save(any(), any());
    }

    @Test
    public void update_shouldReturn() {
        // Given
        CommentDto expected = CommentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.save(expected, TestConstants.ID_1)).willReturn(expected);

        // When
        CommentDto response = controller.update(expected, TestConstants.ID_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).save(expected, TestConstants.ID_1);
        verify(service, times(1)).save(any(), any());
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        CommentDto expected = CommentDto.builder()
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