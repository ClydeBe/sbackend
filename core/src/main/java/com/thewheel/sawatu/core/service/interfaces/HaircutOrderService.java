package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.HaircutOrderDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface HaircutOrderService {
    PageDto<HaircutOrderDto> list(Pageable page, String userdname);
    HaircutOrderDto get(Long id);
    HaircutOrderDto save(HaircutOrderDto haircutOrderDto);
    void delete(HaircutOrderDto haircutOrderDto);
}
