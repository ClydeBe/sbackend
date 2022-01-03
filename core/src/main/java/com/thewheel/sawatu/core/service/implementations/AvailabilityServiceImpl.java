package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.AvailabilityService;
import com.thewheel.sawatu.database.model.Availability;
import com.thewheel.sawatu.database.repository.AvailabilityRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.shared.dto.AvailabilityDto;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import com.thewheel.sawatu.shared.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.thewheel.sawatu.constants.MessageConstants.AVAILABILITY_EXISTS;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final Mapper mapper;
    private final AvailabilityRepository availabilityRepository;

    @Override
    public PageDto<AvailabilityDto> list(Pageable page) {
        Page<Availability> availabilities = availabilityRepository.findAll(page);
        return PageDto.<AvailabilityDto>builder()
                .items(availabilities.stream()
                         .map(mapper::fromEntity)
                         .collect(Collectors.toList()))
                .pages(availabilities.getTotalPages())
                .build();
    }

    @Override
    public AvailabilityDto get(String username) {
        Availability availability = availabilityRepository.findFirstByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.AVAILABILITY_NOT_FOUND,
                                                                             username)));
        return mapper.fromEntity(availability);
    }

    @Override
    public AvailabilityDto save(AvailabilityDto availabilityDto, boolean create) {
        Optional<Availability> a = availabilityRepository.findFirstByUserName(availabilityDto.getUserName());
        if(create && a.isPresent()) throw new BadRequestException(AVAILABILITY_EXISTS);
        Availability availability = mapper.toEntity(availabilityDto);
        availability = availabilityRepository.save(availability);
        return mapper.fromEntity(availability);
    }

    @Override
    public void delete(AvailabilityDto availabilityDto) {
        availabilityRepository.deleteById(availabilityDto.getId());
    }

}
