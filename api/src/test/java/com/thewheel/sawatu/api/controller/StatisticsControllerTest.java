package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.core.service.implementations.StatisticsServiceImpl;
import com.thewheel.sawatu.shared.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.StatisticsDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsControllerTest {

    @InjectMocks
    private StatisticsController controller;

    @Mock
    private StatisticsServiceImpl statisticsService;

    @Test
    public void get_shouldReturn() {
        // Given
        StatisticsDto dto = StatisticsDto.builder()
                .id(TestConstants.ID_1)
                .followersCount((int) TestConstants.NUMERIC_CONSTANT_1)
                .rate((int) TestConstants.NUMERIC_CONSTANT_1)
                .userName(TestConstants.USERNAME_1)
                .build();
        given(statisticsService.get(TestConstants.USERNAME_1)).willReturn(dto);

        // When
        StatisticsDto response = controller.get(TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
        verify(statisticsService, times(1)).get(TestConstants.USERNAME_1);
        verify(statisticsService, times(1)).get(any(String.class));
    }
}