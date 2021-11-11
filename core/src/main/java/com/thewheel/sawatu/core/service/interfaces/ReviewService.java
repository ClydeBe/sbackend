package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ReviewDto;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    PageDto<ReviewDto> list(Pageable page);
    PageDto<ReviewDto> list(Pageable page, String username);
    ReviewDto get(Long id);
    ReviewDto save(ReviewDto reviewDto);
    void delete(ReviewDto reviewDto);
}
