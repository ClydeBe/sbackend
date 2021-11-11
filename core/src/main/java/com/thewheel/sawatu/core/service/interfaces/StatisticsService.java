package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.StatisticsDto;
import org.springframework.data.domain.Pageable;

public interface StatisticsService {
    PageDto<StatisticsDto> list(Pageable page);
    StatisticsDto get(Long id);
    StatisticsDto get(String username);
}
