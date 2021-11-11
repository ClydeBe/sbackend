package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.database.model.Appointment;
import com.thewheel.sawatu.database.repository.AppointmentRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.AppointmentDto;
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
public class AppointmentServiceImplTest {

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Mock
    private Mapper mapper;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Test
    public void listAllByClientName_shouldReturn() {
        // Given
        Appointment appointment_1 = Appointment.builder()
                .id(TestConstants.ID_1)
                .build();
        Appointment appointment_2 = Appointment.builder()
                .id(TestConstants.ID_2)
                .build();
        AppointmentDto dto_1 = AppointmentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        AppointmentDto dto_2 = AppointmentDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<Appointment> orders = new PageImpl<Appointment>(Arrays.asList(appointment_1, appointment_2));
        PageDto<AppointmentDto> expected = PageDto.<AppointmentDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        given(appointmentRepository.findAllByClientNameOrderByIdDesc(TestConstants.USERNAME_1, PAGEABLE)).willReturn(
                orders);
        given(mapper.fromEntity(appointment_1)).willReturn(dto_1);
        given(mapper.fromEntity(appointment_2)).willReturn(dto_2);

        // When
        PageDto<AppointmentDto> response = appointmentService.listAllByClientName(PAGEABLE, TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(appointment_1);
        verify(mapper, times(1)).fromEntity(appointment_2);
        verify(mapper, times(2)).fromEntity(any(Appointment.class));

        verify(appointmentRepository, times(1)).findAllByClientNameOrderByIdDesc(TestConstants.USERNAME_1, PAGEABLE);
        verify(appointmentRepository, times(1)).findAllByClientNameOrderByIdDesc(any(), any(Pageable.class));
    }

    @Test
    public void get_shouldReturn() {
        // Given
        Appointment appointment = Appointment.builder()
                .id(TestConstants.ID_1)
                .build();
        AppointmentDto appointmentDto = AppointmentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(appointmentRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Appointment> of(appointment));
        given(mapper.fromEntity(appointment)).willReturn(appointmentDto);


        // When
        AppointmentDto response = appointmentService.get(TestConstants.ID_1);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(appointmentDto);

        verify(appointmentRepository, times(1)).findById(TestConstants.ID_1);
        verify(appointmentRepository, times(1)).findById(any());

        verify(mapper, times(1)).fromEntity(appointment);
        verify(mapper, times(1)).fromEntity(any(Appointment.class));
    }

    @Test
    public void get_shouldThrow() {
        // Given

        given(appointmentRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Appointment> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> appointmentService.get(TestConstants.ID_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.APPOINTMENT,
                                               TestConstants.ID_1));

        verify(appointmentRepository, times(1)).findById(TestConstants.ID_1);
        verify(appointmentRepository, times(1)).findById(any());
    }

    @Test
    public void save_shouldReturn() {
        // Given
        Appointment appointment = Appointment.builder()
                .id(TestConstants.ID_1)
                .build();
        AppointmentDto dto = AppointmentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(appointmentRepository.save(appointment)).willReturn(appointment);
        given(mapper.fromEntity(appointment)).willReturn(dto);
        given(mapper.toEntity(dto)).willReturn(appointment);


        // When
        AppointmentDto response = appointmentService.save(dto);

        // Then
        assertThat(response).isNotNull()
                .isEqualTo(dto);

        verify(appointmentRepository, times(1)).save(appointment);
        verify(appointmentRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(appointment);
        verify(mapper, times(1)).fromEntity(any(Appointment.class));

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(AppointmentDto.class));
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        AppointmentDto dto = AppointmentDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(appointmentRepository).deleteById(TestConstants.ID_1);

        // When
        appointmentService.delete(dto);

        // Then
        verify(appointmentRepository, times(1)).deleteById(TestConstants.ID_1);
        verify(appointmentRepository, times(1)).deleteById(any());
    }

}