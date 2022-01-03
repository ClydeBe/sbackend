package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.AvailabilityService;
import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.shared.dto.AvailabilityDto;
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
public class AvailabilityControllerTest {

    @InjectMocks
    private AvailabilityController controller;

    @Mock
    private AvailabilityService service;

    @Test
    public void getAll_shouldReturn() {
        // Given
        AvailabilityDto dto_1 = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .build();
        AvailabilityDto dto_2 = AvailabilityDto.builder()
                .id(TestConstants.ID_2)
                .build();
        PageDto<AvailabilityDto> expected = PageDto.<AvailabilityDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(service.list(PAGEABLE)).willReturn(expected);

        // When
        PageDto<AvailabilityDto> response = controller.getAll(Optional.of(0), Optional.of(10));

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
        AvailabilityDto expected = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.get(TestConstants.USERNAME_1)).willReturn(expected);

        // When
        AvailabilityDto response = controller.get(TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).get(TestConstants.USERNAME_1);
        verify(service, times(1)).get(any());
    }

    @Test
    public void create_shouldReturn() {
        // Given
        AvailabilityDto expected = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .userName(TestConstants.USERNAME_1)
                .build();
        given(service.save(expected, true)).willReturn(expected);

        // When
        AvailabilityDto response = controller.create(expected, TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).save(expected, true);
    }

    @Test
    public void update_shouldReturn() {
        // Given
        AvailabilityDto expected = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .userName(TestConstants.USERNAME_1)
                .build();
        given(service.save(expected, false)).willReturn(expected);

        // When
        AvailabilityDto response = controller.update(expected, TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).save(expected, false);
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        AvailabilityDto expected = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .userName(TestConstants.USERNAME_1)
                .build();
        doNothing().when(service).delete(expected);

        // When
        controller.delete(expected, TestConstants.USERNAME_1);

        // Then
        verify(service, times(1)).delete(expected);
        verify(service, times(1)).delete(any());
    }

}