package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.HaircutOrderService;
import com.thewheel.sawatu.database.model.HaircutOrder;
import com.thewheel.sawatu.database.repository.HaircutOrderRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.dto.HaircutOrderDto;
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
public class HaircutOrderServiceImpl implements HaircutOrderService {

    private final Mapper mapper;
    private final HaircutOrderRepository haircutOrderRepository;

    @Override
    public PageDto<HaircutOrderDto> list(Pageable page, String username) {
        Page<HaircutOrder> haircutOrders = haircutOrderRepository.findAllByClientNameOrderByIdDesc(username, page);
        return PageDto.<HaircutOrderDto>builder()
                .items(haircutOrders.stream()
                         .map(mapper::fromEntity)
                         .collect(Collectors.toList()))
                .pages(haircutOrders.getTotalPages())
                .build();
    }

    @Override
    public HaircutOrderDto get(Long id) {
        HaircutOrder haircutOrder = haircutOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                                                             MessageConstants.HAIRCUT_ORDER,
                                                                             id)));
        return mapper.fromEntity(haircutOrder);
    }

    @Override
    public HaircutOrderDto save(HaircutOrderDto haircutOrderDto) {
        HaircutOrder haircutOrder = mapper.toEntity(haircutOrderDto);
        haircutOrder = haircutOrderRepository.save(haircutOrder);
        return mapper.fromEntity(haircutOrder);
    }

    @Override
    public void delete(HaircutOrderDto haircutOrderDto) {
        haircutOrderRepository.deleteById(haircutOrderDto.getId());
    }
}
