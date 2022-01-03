package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.database.model.Availability;
import com.thewheel.sawatu.database.repository.AvailabilityRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.shared.dto.AvailabilityDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import com.thewheel.sawatu.shared.exception.BadRequestException;
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
public class AvailabilityServiceImplTest {

    @InjectMocks
    private AvailabilityServiceImpl availabilityService;

    @Mock
    private Mapper mapper;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Test
    public void list_shouldReturn() {
        // Given
        Availability entity_1 = Availability.builder()
                .id(TestConstants.ID_1)
                .build();
        Availability entity_2 = Availability.builder()
                .id(TestConstants.ID_2)
                .build();
        AvailabilityDto dto_1 = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .build();
        AvailabilityDto dto_2 = AvailabilityDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<Availability> orders = new PageImpl<Availability>(Arrays.asList(entity_1, entity_2));
        PageDto<AvailabilityDto> expected = PageDto.<AvailabilityDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        given(availabilityRepository.findAll(PAGEABLE)).willReturn(
                orders);
        given(mapper.fromEntity(entity_1)).willReturn(dto_1);
        given(mapper.fromEntity(entity_2)).willReturn(dto_2);

        // When
        PageDto<AvailabilityDto> response = availabilityService.list(PAGEABLE);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(entity_1);
        verify(mapper, times(1)).fromEntity(entity_2);
        verify(mapper, times(2)).fromEntity(any(Availability.class));

        verify(availabilityRepository, times(1)).findAll(PAGEABLE);
        verify(availabilityRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void get_shouldReturn() {
        // Given
        Availability entity = Availability.builder()
                .id(TestConstants.ID_1)
                .build();
        AvailabilityDto dto = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(availabilityRepository.findFirstByUserName(TestConstants.USERNAME_1)).willReturn(
                Optional.<Availability> of(entity));
        given(mapper.fromEntity(entity)).willReturn(dto);


        // When
        AvailabilityDto response = availabilityService.get(TestConstants.USERNAME_1);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(dto);

        verify(availabilityRepository, times(1)).findById(TestConstants.ID_1);
        verify(availabilityRepository, times(1)).findById(any());

        verify(mapper, times(1)).fromEntity(entity);
        verify(mapper, times(1)).fromEntity(any(Availability.class));
    }

    @Test
    public void get_shouldThrow() {
        // Given

        given(availabilityRepository.findFirstByUserName(TestConstants.USERNAME_1)).willReturn(
                Optional.<Availability> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> availabilityService.get(TestConstants.USERNAME_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.AVAILABILITY,
                                               TestConstants.ID_1));

        verify(availabilityRepository, times(1)).findById(TestConstants.ID_1);
        verify(availabilityRepository, times(1)).findById(any());
    }

    @Test
    public void save_whenCreating_shouldReturn() {
        // Given
        Availability entity = Availability.builder()
                .id(TestConstants.ID_1)
                .userName(TestConstants.USERNAME_1)
                .build();
        AvailabilityDto dto = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .userName(TestConstants.USERNAME_1)
                .build();
        given(availabilityRepository.findFirstByUserName(TestConstants.USERNAME_1)).willReturn(
                Optional.<Availability> empty());
        given(availabilityRepository.save(entity)).willReturn(entity);
        given(mapper.fromEntity(entity)).willReturn(dto);
        given(mapper.toEntity(dto)).willReturn(entity);


        // When
        AvailabilityDto response = availabilityService.save(dto, true);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(dto);

        verify(availabilityRepository, times(1)).findFirstByUserName(TestConstants.USERNAME_1);
        verify(availabilityRepository, times(1)).findFirstByUserName(any());

        verify(availabilityRepository, times(1)).save(entity);
        verify(availabilityRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(entity);
        verify(mapper, times(1)).fromEntity(any(Availability.class));

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(AvailabilityDto.class));
    }

    @Test
    public void save_whenCreating_shouldThrow() {
        // Given
        Availability entity = Availability.builder()
                .id(TestConstants.ID_1)
                .userName(TestConstants.USERNAME_1)
                .build();
        AvailabilityDto dto = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .userName(TestConstants.USERNAME_1)
                .build();
        given(availabilityRepository.findFirstByUserName(TestConstants.USERNAME_1)).willReturn(
                Optional.<Availability> of(entity));


        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> availabilityService.save(dto, true));

        // Then
        response.isNotNull()
                .isInstanceOf(BadRequestException.class)
                .withFailMessage(MessageConstants.AVAILABILITY_EXISTS);

        verify(availabilityRepository, times(1)).findFirstByUserName(TestConstants.USERNAME_1);
        verify(availabilityRepository, times(1)).findFirstByUserName(any());

    }

    @Test
    public void save_whenUpdating_shouldReturn() {
        // Given
        Availability entity = Availability.builder()
                .id(TestConstants.ID_1)
                .userName(TestConstants.USERNAME_1)
                .build();
        AvailabilityDto dto = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .userName(TestConstants.USERNAME_1)
                .build();
        given(availabilityRepository.findFirstByUserName(TestConstants.USERNAME_1)).willReturn(
                Optional.<Availability> of(entity));
        given(availabilityRepository.save(entity)).willReturn(entity);
        given(mapper.fromEntity(entity)).willReturn(dto);
        given(mapper.toEntity(dto)).willReturn(entity);


        // When
        AvailabilityDto response = availabilityService.save(dto, false);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(dto);

        verify(availabilityRepository, times(1)).findFirstByUserName(TestConstants.USERNAME_1);
        verify(availabilityRepository, times(1)).findFirstByUserName(any());

        verify(availabilityRepository, times(1)).save(entity);
        verify(availabilityRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(entity);
        verify(mapper, times(1)).fromEntity(any(Availability.class));

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(AvailabilityDto.class));
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        AvailabilityDto dto = AvailabilityDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(availabilityRepository).deleteById(TestConstants.ID_1);

        // When
        availabilityService.delete(dto);

        // Then
        verify(availabilityRepository, times(1)).deleteById(TestConstants.ID_1);
        verify(availabilityRepository, times(1)).deleteById(any());
    }

}