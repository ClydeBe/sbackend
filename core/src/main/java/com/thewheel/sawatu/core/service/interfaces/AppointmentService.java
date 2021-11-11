package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.AppointmentDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    PageDto<AppointmentDto> listAllByClientName(Pageable page, String username);
    AppointmentDto get(Long id);
    AppointmentDto save(AppointmentDto appointmentDto);
    void delete(AppointmentDto appointmentDto);
}
