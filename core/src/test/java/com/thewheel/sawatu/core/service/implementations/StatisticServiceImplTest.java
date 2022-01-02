package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.database.model.Statistics;
import com.thewheel.sawatu.database.repository.StatisticsRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.StatisticsDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceImplTest {

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Mock
    private Mapper mapper;

    @Mock
    private StatisticsRepository statisticsRepository;

    @Test
    public void list_shouldReturn() {
        // Given
        Statistics stat_1 = Statistics.builder()
                .id(TestConstants.ID_1)
                .build();
        Statistics stat_2 = Statistics.builder()
                .id(TestConstants.ID_2)
                .build();
        StatisticsDto statDto_1 = StatisticsDto.builder()
                .id(TestConstants.ID_1)
                .build();
        StatisticsDto statDto_2 = StatisticsDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Page<Statistics> statistics = new PageImpl(Arrays.asList(stat_1, stat_2));
        PageDto<StatisticsDto> expected = PageDto.<StatisticsDto> builder()
                .items(Arrays.asList(statDto_1, statDto_2))
                .pages(1)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(mapper.fromEntity(stat_1)).willReturn(statDto_1);
        given(mapper.fromEntity(stat_2)).willReturn(statDto_2);
        given(statisticsRepository.findAll(PAGEABLE)).willReturn(statistics);

        // When
        PageDto<StatisticsDto> response = statisticsService.list(PAGEABLE);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(statisticsRepository, times(1)).findAll(PAGEABLE);
        verify(statisticsRepository, times(1)).findAll(any(Pageable.class));

        verify(mapper, times(1)).fromEntity(stat_1);
        verify(mapper, times(1)).fromEntity(stat_2);
        verify(mapper, times(2)).fromEntity(any(Statistics.class));
    }

    @Test
    public void get_byId_shouldThrow() {
        // Given
        given(statisticsRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Statistics> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> statisticsService.get(TestConstants.ID_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.STATISTICS,
                                               TestConstants.ID_1));
    }

    @Test
    public void get_byUsername_shouldThrow() {
        // Given
        given(statisticsRepository.findFirstByUserNameOrderByIdDesc(TestConstants.USERNAME_1)).willReturn(
                Optional.<Statistics> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> statisticsService.get(TestConstants.USERNAME_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.STATISTICS,
                                               TestConstants.ID_1));
    }

    @Test
    public void test_byId_shouldReturn() {
        // Given
        Statistics stat = Statistics.builder()
                .id(1L)
                .build();
        StatisticsDto statDto = StatisticsDto.builder()
                .id(1L)
                .build();
        given(statisticsRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Statistics>of(stat));
        given(mapper.fromEntity(stat)).willReturn(statDto);

        // When
        StatisticsDto response = statisticsService.get(TestConstants.ID_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(statDto);
    }

    @Test
    public void test_byUsername_shouldReturn() {
        // Given
        Statistics stat = Statistics.builder()
                .id(1L)
                .build();
        StatisticsDto statDto = StatisticsDto.builder()
                .id(1L)
                .build();
        given(statisticsRepository.findFirstByUserNameOrderByIdDesc(TestConstants.USERNAME_1)).willReturn(Optional.<Statistics>of(stat));
        given(mapper.fromEntity(stat)).willReturn(statDto);

        // When
        StatisticsDto response = statisticsService.get(TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(statDto);
    }

}