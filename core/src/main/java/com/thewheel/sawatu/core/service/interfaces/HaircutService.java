package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.HaircutDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface HaircutService {
    PageDto<HaircutDto> list(Pageable page);
    HaircutDto get(Long id);
    HaircutDto save(HaircutDto haircutDto);
    void delete(HaircutDto haircutDto);
}
