package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.HaircutService;
import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.shared.dto.HaircutDto;
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
public class HaircutControllerTest {

    @InjectMocks
    private HaircutController controller;

    @Mock
    private HaircutService service;

    @Test
    public void getAll_shouldReturn() {
        // Given
        HaircutDto dto_1 = HaircutDto.builder()
                .id(TestConstants.ID_1)
                .build();
        HaircutDto dto_2 = HaircutDto.builder()
                .id(TestConstants.ID_2)
                .build();
        PageDto<HaircutDto> expected = PageDto.<HaircutDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(service.list(PAGEABLE)).willReturn(expected);

        // When
        PageDto<HaircutDto> response = controller.getAll(Optional.of(0), Optional.of(10));

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).list(PAGEABLE);
        verify(service, times(1)).list(any());

    }

    @Test
    public void get_shouldReturn() {
        // Given
        HaircutDto expected = HaircutDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.get(TestConstants.ID_1)).willReturn(expected);

        // When
        HaircutDto response = controller.get(TestConstants.ID_1);

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
        HaircutDto expected = HaircutDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.save(expected)).willReturn(expected);

        // When
        HaircutDto response = controller.create(expected);

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
        HaircutDto expected = HaircutDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.save(expected)).willReturn(expected);

        // When
        HaircutDto response = controller.update(expected);

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
        HaircutDto expected = HaircutDto.builder()
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