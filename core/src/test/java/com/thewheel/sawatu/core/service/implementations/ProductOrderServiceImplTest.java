package com.thewheel.sawatu.core.service.implementations;


import com.thewheel.sawatu.database.model.ProductOrder;
import com.thewheel.sawatu.database.repository.ProductOrderRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProductOrderDto;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductOrderServiceImplTest {

    @InjectMocks
    private ProductOrderServiceImpl productOrderService;

    @Mock
    private Mapper mapper;

    @Mock
    private ProductOrderRepository productOrderRepository;

    @Test
    public void list_shouldReturn() {
        // Given
        ProductOrder order_1 = ProductOrder.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductOrder order_2 = ProductOrder.builder()
                .id(TestConstants.ID_2)
                .build();
        ProductOrderDto orderDto_1 = ProductOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductOrderDto orderDto_2 = ProductOrderDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<ProductOrder> orders = new PageImpl<>(Arrays.asList(order_1, order_2));
        PageDto<ProductOrderDto> expected = PageDto.<ProductOrderDto> builder()
                .items(Arrays.asList(orderDto_1, orderDto_2))
                .pages(1)
                .build();
        given(productOrderRepository.findAll(PAGEABLE)).willReturn(orders);
        given(mapper.fromEntity(order_1)).willReturn(orderDto_1);
        given(mapper.fromEntity(order_2)).willReturn(orderDto_2);

        // When
        PageDto<ProductOrderDto> response = productOrderService.list(PAGEABLE);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(order_1);
        verify(mapper, times(1)).fromEntity(order_2);
        verify(mapper, times(2)).fromEntity(any(ProductOrder.class));

        verify(productOrderRepository, times(1)).findAll(PAGEABLE);
        verify(productOrderRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void list_byUsername_shouldReturn() {
        // Given
        ProductOrder order_1 = ProductOrder.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductOrder order_2 = ProductOrder.builder()
                .id(TestConstants.ID_2)
                .build();
        ProductOrderDto orderDto_1 = ProductOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductOrderDto orderDto_2 = ProductOrderDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<ProductOrder> orders = new PageImpl<>(Arrays.asList(order_1, order_2));
        PageDto<ProductOrderDto> expected = PageDto.<ProductOrderDto> builder()
                .items(Arrays.asList(orderDto_1, orderDto_2))
                .pages(1)
                .build();
        given(productOrderRepository.findAllByUserNameOrderByIdDesc(TestConstants.USERNAME_1, PAGEABLE)).willReturn(
                orders);
        given(mapper.fromEntity(order_1)).willReturn(orderDto_1);
        given(mapper.fromEntity(order_2)).willReturn(orderDto_2);

        // When
        PageDto<ProductOrderDto> response = productOrderService.list(PAGEABLE, TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(order_1);
        verify(mapper, times(1)).fromEntity(order_2);
        verify(mapper, times(2)).fromEntity(any(ProductOrder.class));

        verify(productOrderRepository, times(1)).findAllByUserNameOrderByIdDesc(TestConstants.USERNAME_1, PAGEABLE);
        verify(productOrderRepository, times(1)).findAllByUserNameOrderByIdDesc(any(), any(Pageable.class));
    }

    @Test
    public void get_shouldReturn() {
        // Given
        ProductOrder order = ProductOrder.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductOrderDto orderDto = ProductOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(productOrderRepository.findById(TestConstants.ID_1)).willReturn(Optional.<ProductOrder> of(order));
        given(mapper.fromEntity(order)).willReturn(orderDto);


        // When
        ProductOrderDto response = productOrderService.get(TestConstants.ID_1);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(orderDto);

        verify(productOrderRepository, times(1)).findById(TestConstants.ID_1);
        verify(productOrderRepository, times(1)).findById(any());

        verify(mapper, times(1)).fromEntity(order);
        verify(mapper, times(1)).fromEntity(any(ProductOrder.class));
    }

    @Test
    public void get_shouldThrow() {
        // Given

        given(productOrderRepository.findById(TestConstants.ID_1)).willReturn(Optional.<ProductOrder> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> productOrderService.get(TestConstants.ID_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.PRODUCT_ORDER,
                                               TestConstants.ID_1));

        verify(productOrderRepository, times(1)).findById(TestConstants.ID_1);
        verify(productOrderRepository, times(1)).findById(any());
    }

    @Test
    public void save_shouldReturn() {
        // Given
        ProductOrder order = ProductOrder.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductOrderDto orderDto = ProductOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(productOrderRepository.save(order)).willReturn(order);
        given(mapper.fromEntity(order)).willReturn(orderDto);
        given(mapper.toEntity(orderDto)).willReturn(order);


        // When
        ProductOrderDto response = productOrderService.save(orderDto);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(orderDto);

        verify(productOrderRepository, times(1)).save(order);
        verify(productOrderRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(order);
        verify(mapper, times(1)).fromEntity(any(ProductOrder.class));

        verify(mapper, times(1)).toEntity(orderDto);
        verify(mapper, times(1)).toEntity(any(ProductOrderDto.class));
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        ProductOrderDto dto = ProductOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(productOrderRepository).deleteById(TestConstants.ID_1);

        // When
        productOrderService.delete(dto);

        // Then
        verify(productOrderRepository, times(1)).deleteById(TestConstants.ID_1);
        verify(productOrderRepository, times(1)).deleteById(any());
    }
}