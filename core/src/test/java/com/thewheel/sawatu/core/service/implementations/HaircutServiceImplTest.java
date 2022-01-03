package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.database.model.Haircut;
import com.thewheel.sawatu.database.repository.HaircutRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.shared.dto.HaircutDto;
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
public class HaircutServiceImplTest {

    @InjectMocks
    private HaircutServiceImpl haircutService;

    @Mock
    private Mapper mapper;

    @Mock
    private HaircutRepository haircutRepository;

    @Test
    public void list_shouldReturn() {
        // Given
        Haircut haircut_1 = Haircut.builder()
                .id(TestConstants.ID_1)
                .build();
        Haircut haircut_2 = Haircut.builder()
                .id(TestConstants.ID_2)
                .build();
        HaircutDto dto_1 = HaircutDto.builder()
                .id(TestConstants.ID_1)
                .build();
        HaircutDto dto_2 = HaircutDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<Haircut> orders = new PageImpl<Haircut>(Arrays.asList(haircut_1, haircut_2));
        PageDto<HaircutDto> expected = PageDto.<HaircutDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        given(haircutRepository.findAll(PAGEABLE)).willReturn(orders);
        given(mapper.fromEntity(haircut_1)).willReturn(dto_1);
        given(mapper.fromEntity(haircut_2)).willReturn(dto_2);

        // When
        PageDto<HaircutDto> response = haircutService.list(PAGEABLE);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(haircut_1);
        verify(mapper, times(1)).fromEntity(haircut_2);
        verify(mapper, times(2)).fromEntity(any(Haircut.class));

        verify(haircutRepository, times(1)).findAll(PAGEABLE);
        verify(haircutRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void get_shouldReturn() {
        // Given
        Haircut haircut = Haircut.builder()
                .id(TestConstants.ID_1)
                .build();
        HaircutDto orderDto = HaircutDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(haircutRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Haircut> of(haircut));
        given(mapper.fromEntity(haircut)).willReturn(orderDto);


        // When
        HaircutDto response = haircutService.get(TestConstants.ID_1);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(orderDto);

        verify(haircutRepository, times(1)).findById(TestConstants.ID_1);
        verify(haircutRepository, times(1)).findById(any());

        verify(mapper, times(1)).fromEntity(haircut);
        verify(mapper, times(1)).fromEntity(any(Haircut.class));
    }

    @Test
    public void get_shouldThrow() {
        // Given

        given(haircutRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Haircut> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> haircutService.get(TestConstants.ID_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.HAIRCUT,
                                               TestConstants.ID_1));

        verify(haircutRepository, times(1)).findById(TestConstants.ID_1);
        verify(haircutRepository, times(1)).findById(any());
    }

    @Test
    public void save_shouldReturn() {
        // Given
        Haircut haircut = Haircut.builder()
                .id(TestConstants.ID_1)
                .build();
        HaircutDto haircutDto = HaircutDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(haircutRepository.save(haircut)).willReturn(haircut);
        given(mapper.fromEntity(haircut)).willReturn(haircutDto);
        given(mapper.toEntity(haircutDto)).willReturn(haircut);


        // When
        HaircutDto response = haircutService.save(haircutDto);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(haircutDto);

        verify(haircutRepository, times(1)).save(haircut);
        verify(haircutRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(haircut);
        verify(mapper, times(1)).fromEntity(any(Haircut.class));

        verify(mapper, times(1)).toEntity(haircutDto);
        verify(mapper, times(1)).toEntity(any(HaircutDto.class));
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        HaircutDto dto = HaircutDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(haircutRepository).deleteById(TestConstants.ID_1);

        // When
        haircutService.delete(dto);

        // Then
        verify(haircutRepository, times(1)).deleteById(TestConstants.ID_1);
        verify(haircutRepository, times(1)).deleteById(any());
    }

}