package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.HaircutService;
import com.thewheel.sawatu.database.model.Haircut;
import com.thewheel.sawatu.database.repository.HaircutRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.dto.HaircutDto;
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
public class HaircutServiceImpl implements HaircutService {

    private final Mapper mapper;
    private final HaircutRepository haircutRepository;

    @Override
    public PageDto<HaircutDto> list(Pageable page) {
        Page<Haircut> haircuts = haircutRepository.findAll(page);
        return PageDto.<HaircutDto>builder()
                .items(haircuts.stream()
                         .map(mapper::fromEntity)
                         .collect(Collectors.toList()))
                .pages(haircuts.getTotalPages())
                .build();
    }

    @Override
    public HaircutDto get(Long id) {
        Haircut haircut = haircutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                                                             MessageConstants.HAIRCUT,
                                                                             id)));
        return mapper.fromEntity(haircut);
    }

    @Override
    public HaircutDto save(HaircutDto haircutDto) {
        Haircut haircut = mapper.toEntity(haircutDto);
        haircut = haircutRepository.save(haircut);
        return mapper.fromEntity(haircut);
    }

    @Override
    public void delete(HaircutDto haircutDto) {
        haircutRepository.deleteById(haircutDto.getId());
    }
}
