package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.AvailabilityDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AvailabilityService {
    PageDto<AvailabilityDto> list(Pageable page, String username);
    AvailabilityDto get(Long id);
    AvailabilityDto save(AvailabilityDto AvailabilityDto);
    void delete(AvailabilityDto AvailabilityDto);
}
