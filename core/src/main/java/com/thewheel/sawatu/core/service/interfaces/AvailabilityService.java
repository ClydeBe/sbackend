package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.AvailabilityDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import org.springframework.data.domain.Pageable;

public interface AvailabilityService {
    PageDto<AvailabilityDto> list(Pageable page);
    AvailabilityDto get(String username);
    AvailabilityDto save(AvailabilityDto AvailabilityDto, boolean create);
    void delete(AvailabilityDto AvailabilityDto);
}
