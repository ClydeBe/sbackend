package com.thewheel.sawatu.shared.dto;

import com.thewheel.sawatu.shared.dto.validator.annotations.PeriodsValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodDto {

    private LocalDate day;

    @PeriodsValid
    private List<Integer> periods;

}
