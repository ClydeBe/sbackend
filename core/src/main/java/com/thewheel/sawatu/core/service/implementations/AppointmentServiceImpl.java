package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.AppointmentService;
import com.thewheel.sawatu.database.model.Appointment;
import com.thewheel.sawatu.database.repository.AppointmentRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.shared.dto.AppointmentDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final Mapper mapper;

    @Override
    public PageDto<AppointmentDto> listAllByClientName(Pageable page, String username) {
        Page<Appointment> appointments = appointmentRepository.findAllByClientNameOrderByIdDesc(username, page);
        return PageDto.<AppointmentDto> builder()
                .items(appointments.stream()
                                  .map(mapper::fromEntity)
                                  .collect(Collectors.toList()))
                .pages(appointments.getTotalPages())
                .build();
    }

    @Override
    public AppointmentDto get(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                                                             MessageConstants.APPOINTMENT,
                                                                             id)));
        return mapper.fromEntity(appointment);
    }

    @Override
    public AppointmentDto save(AppointmentDto appointmentDto) {
        Appointment appointment = mapper.toEntity(appointmentDto);
        appointment = appointmentRepository.save(appointment);
        return mapper.fromEntity(appointment);
    }

    @Override
    public void delete(AppointmentDto appointmentDto) {
        appointmentRepository.deleteById(appointmentDto.getId());
    }


}
