package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.AvailabilityService;
import com.thewheel.sawatu.database.model.Availability;
import com.thewheel.sawatu.database.repository.AvailabilityRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.dto.AvailabilityDto;
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
public class AvailabilityServiceImpl implements AvailabilityService {

    private final Mapper mapper;
    private final AvailabilityRepository availabilityRepository;

    @Override
    public PageDto<AvailabilityDto> list(Pageable page, String username) {
        Page<Availability> availabilities = availabilityRepository.findAllByUserNameOrderByIdDesc(username, page);
        return PageDto.<AvailabilityDto>builder()
                .items(availabilities.stream()
                         .map(mapper::fromEntity)
                         .collect(Collectors.toList()))
                .pages(availabilities.getTotalPages())
                .build();
    }

    @Override
    public AvailabilityDto get(Long id) {
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                                                             MessageConstants.AVAILABILITY,
                                                                             id)));
        return mapper.fromEntity(availability);
    }

    @Override
    public AvailabilityDto save(AvailabilityDto availabilityDto) {
        Availability availability = mapper.toEntity(availabilityDto);
        availability = availabilityRepository.save(availability);
        return mapper.fromEntity(availability);
    }

    @Override
    public void delete(AvailabilityDto availabilityDto) {
        availabilityRepository.deleteById(availabilityDto.getId());
    }

}
