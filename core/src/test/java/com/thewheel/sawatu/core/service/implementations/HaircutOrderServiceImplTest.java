package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.database.model.HaircutOrder;
import com.thewheel.sawatu.database.repository.HaircutOrderRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.HaircutOrderDto;
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
public class HaircutOrderServiceImplTest {

    @InjectMocks
    private HaircutOrderServiceImpl haircutOrderService;

    @Mock
    private Mapper mapper;

    @Mock
    private HaircutOrderRepository haircutOrderRepository;

    @Test
    public void list_shouldReturn() {
        // Given
        HaircutOrder entity_1 = HaircutOrder.builder()
                .id(TestConstants.ID_1)
                .build();
        HaircutOrder entity_2 = HaircutOrder.builder()
                .id(TestConstants.ID_2)
                .build();
        HaircutOrderDto dto_1 = HaircutOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        HaircutOrderDto dto_2 = HaircutOrderDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<HaircutOrder> orders = new PageImpl<HaircutOrder>(Arrays.asList(entity_1, entity_2));
        PageDto<HaircutOrderDto> expected = PageDto.<HaircutOrderDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        given(haircutOrderRepository.findAllByClientNameOrderByIdDesc(TestConstants.USERNAME_1, PAGEABLE)).willReturn(
                orders);
        given(mapper.fromEntity(entity_1)).willReturn(dto_1);
        given(mapper.fromEntity(entity_2)).willReturn(dto_2);

        // When
        PageDto<HaircutOrderDto> response = haircutOrderService.list(PAGEABLE, TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(entity_1);
        verify(mapper, times(1)).fromEntity(entity_2);
        verify(mapper, times(2)).fromEntity(any(HaircutOrder.class));

        verify(haircutOrderRepository, times(1)).findAllByClientNameOrderByIdDesc(TestConstants.USERNAME_1,
                                                                                   PAGEABLE);
        verify(haircutOrderRepository, times(1)).findAllByClientNameOrderByIdDesc(any(), any(Pageable.class));
    }

    @Test
    public void get_shouldReturn() {
        // Given
        HaircutOrder entity = HaircutOrder.builder()
                .id(TestConstants.ID_1)
                .build();
        HaircutOrderDto dto = HaircutOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(haircutOrderRepository.findById(TestConstants.ID_1)).willReturn(Optional.<HaircutOrder> of(entity));
        given(mapper.fromEntity(entity)).willReturn(dto);


        // When
        HaircutOrderDto response = haircutOrderService.get(TestConstants.ID_1);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(dto);

        verify(haircutOrderRepository, times(1)).findById(TestConstants.ID_1);
        verify(haircutOrderRepository, times(1)).findById(any());

        verify(mapper, times(1)).fromEntity(entity);
        verify(mapper, times(1)).fromEntity(any(HaircutOrder.class));
    }

    @Test
    public void get_shouldThrow() {
        // Given

        given(haircutOrderRepository.findById(TestConstants.ID_1)).willReturn(Optional.<HaircutOrder> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> haircutOrderService.get(TestConstants.ID_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.HAIRCUT_ORDER,
                                               TestConstants.ID_1));

        verify(haircutOrderRepository, times(1)).findById(TestConstants.ID_1);
        verify(haircutOrderRepository, times(1)).findById(any());
    }

    @Test
    public void save_shouldReturn() {
        // Given
        HaircutOrder entity = HaircutOrder.builder()
                .id(TestConstants.ID_1)
                .build();
        HaircutOrderDto dto = HaircutOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(haircutOrderRepository.save(entity)).willReturn(entity);
        given(mapper.fromEntity(entity)).willReturn(dto);
        given(mapper.toEntity(dto)).willReturn(entity);


        // When
        HaircutOrderDto response = haircutOrderService.save(dto);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(dto);

        verify(haircutOrderRepository, times(1)).save(entity);
        verify(haircutOrderRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(entity);
        verify(mapper, times(1)).fromEntity(any(HaircutOrder.class));

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(HaircutOrderDto.class));
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        HaircutOrderDto dto = HaircutOrderDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(haircutOrderRepository).deleteById(TestConstants.ID_1);

        // When
        haircutOrderService.delete(dto);

        // Then
        verify(haircutOrderRepository, times(1)).deleteById(TestConstants.ID_1);
        verify(haircutOrderRepository, times(1)).deleteById(any());
    }

}