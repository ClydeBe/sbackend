package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.StatisticsService;
import com.thewheel.sawatu.database.model.Statistics;
import com.thewheel.sawatu.database.repository.StatisticsRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.StatisticsDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final Mapper mapper;
    private final StatisticsRepository statisticsRepository;

    @Override
    public PageDto<StatisticsDto> list(Pageable page) {
        Page<Statistics> statistics = statisticsRepository.findAll(page);
        return PageDto.<StatisticsDto> builder()
                .items(statistics.stream()
                                  .map(mapper::fromEntity)
                                  .collect(Collectors.toList()))
                .pages(statistics.getTotalPages())
                .build();
    }

    @Override
    public StatisticsDto get(Long id) {
        Statistics statistics = statisticsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                                                             MessageConstants.STATISTICS,
                                                                             id)));
        return mapper.fromEntity(statistics);
    }

    @Override
    public StatisticsDto get(String username) {
        Statistics statistics = statisticsRepository.findFirstByUserNameOrderByIdDesc(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(MessageConstants.STATISTICS_ARE_NOT_YET_COMPUTED, username)));
        return mapper.fromEntity(statistics);
    }
}
