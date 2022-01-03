package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.ReviewService;
import com.thewheel.sawatu.database.model.Reviews;
import com.thewheel.sawatu.database.repository.ReviewsRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ReviewDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final Mapper mapper;
    private final ReviewsRepository reviewsRepository;

    @Override
    public PageDto<ReviewDto> list(Pageable page) {
        Page<Reviews> reviews = reviewsRepository.findAll(page);
        return PageDto.<ReviewDto>builder()
                .items(reviews.stream()
                         .map(mapper::fromEntity)
                         .collect(Collectors.toList()))
                .pages(reviews.getTotalPages())
                .build();
    }

    @Override
    public PageDto<ReviewDto> list(Pageable page, String username) {
        Page<Reviews> reviews = reviewsRepository.findAllBySelfNameOrderByIdDesc(username, page);
        return PageDto.<ReviewDto>builder()
                .items(reviews.stream()
                                  .map(mapper::fromEntity)
                                  .collect(Collectors.toList()))
                .pages(reviews.getTotalPages())
                .build();
    }

    @Override
    public ReviewDto get(Long id) {
        Reviews reviews = reviewsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                                                             MessageConstants.REVIEWS,
                                                                             id)));
        return mapper.fromEntity(reviews);
    }

    @Override
    public ReviewDto save(ReviewDto reviewDto) {
        Reviews reviews = mapper.toEntity(reviewDto);
        reviews = reviewsRepository.save(reviews);
        return mapper.fromEntity(reviews);
    }

    @Override
    public void delete(ReviewDto reviewDto) {
        reviewsRepository.deleteById(reviewDto.getId());
    }
}
