package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.interfaces.ProductOrderService;
import com.thewheel.sawatu.shared.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProductOrderDto;
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
public class ProductOrderControllerTest {

    @InjectMocks
    private ProductOrderController controller;

    @Mock
    private ProductOrderService service;

    @Test
    public void getAll_shouldReturn() {
        // Given
        ProductOrderDto dto_1 = ProductOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductOrderDto dto_2 = ProductOrderDto.builder()
                .id(TestConstants.ID_2)
                .build();
        PageDto<ProductOrderDto> expected = PageDto.<ProductOrderDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(service.list(PAGEABLE, TestConstants.USERNAME_1)).willReturn(expected);

        // When
        PageDto<ProductOrderDto> response = controller.getAll(TestConstants.USERNAME_1, Optional.of(0), Optional.of(10));

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).list(PAGEABLE, TestConstants.USERNAME_1);
        verify(service, times(1)).list(any(), any());

    }

    @Test
    public void get_shouldReturn() {
        // Given
        ProductOrderDto expected = ProductOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.get(TestConstants.ID_1)).willReturn(expected);

        // When
        ProductOrderDto response = controller.get(TestConstants.ID_1);

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
        ProductOrderDto expected = ProductOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.save(expected)).willReturn(expected);

        // When
        ProductOrderDto response = controller.create(expected);

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
        ProductOrderDto expected = ProductOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.save(expected)).willReturn(expected);

        // When
        ProductOrderDto response = controller.update(expected);

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
        ProductOrderDto expected = ProductOrderDto.builder()
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