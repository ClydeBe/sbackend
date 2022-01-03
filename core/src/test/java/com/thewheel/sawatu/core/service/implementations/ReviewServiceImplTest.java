package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.database.model.Reviews;
import com.thewheel.sawatu.database.repository.ReviewsRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ReviewDto;
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
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private Mapper mapper;

    @Mock
    private ReviewsRepository reviewsRepository;

    @Test
    public void list_shouldReturn() {
        // Given
        Reviews review_1 = Reviews.builder()
                .id(TestConstants.ID_1)
                .build();
        Reviews review_2 = Reviews.builder()
                .id(TestConstants.ID_2)
                .build();
        Reviews review_3 = Reviews.builder()
                .id(TestConstants.ID_3)
                .build();
        ReviewDto reviewDto_1 = ReviewDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ReviewDto reviewDto_2 = ReviewDto.builder()
                .id(TestConstants.ID_2)
                .build();
        ReviewDto reviewDto_3 = ReviewDto.builder()
                .id(TestConstants.ID_3)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<Reviews> reviews = new PageImpl<Reviews>(Arrays.asList(review_1, review_2, review_3));
        PageDto<ReviewDto> expected = PageDto.<ReviewDto> builder()
                .items(Arrays.asList(reviewDto_1, reviewDto_2, reviewDto_3))
                .pages(1)
                .build();
        given(reviewsRepository.findAll(PAGEABLE)).willReturn(reviews);
        given(mapper.fromEntity(review_1)).willReturn(reviewDto_1);
        given(mapper.fromEntity(review_2)).willReturn(reviewDto_2);
        given(mapper.fromEntity(review_3)).willReturn(reviewDto_3);

        // When
        PageDto<ReviewDto> response = reviewService.list(PAGEABLE);


        // Then
        assertThat(response)
                .isNotNull()
                .isInstanceOf(PageDto.class)
                .isEqualTo(expected);

        verify(reviewsRepository, times(1)).findAll(PAGEABLE);
        verify(reviewsRepository, times(1)).findAll(any(Pageable.class));

        verify(mapper, times(1)).fromEntity(review_1);
        verify(mapper, times(1)).fromEntity(review_2);
        verify(mapper, times(1)).fromEntity(review_3);
        verify(mapper, times(3)).fromEntity(any(Reviews.class));
    }

    @Test
    public void List_wIthUsername_shouldReturn() {
        // Given
        Reviews review_1 = Reviews.builder()
                .id(TestConstants.ID_1)
                .build();
        Reviews review_2 = Reviews.builder()
                .id(TestConstants.ID_2)
                .build();
        Reviews review_3 = Reviews.builder()
                .id(TestConstants.ID_3)
                .build();
        ReviewDto reviewDto_1 = ReviewDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ReviewDto reviewDto_2 = ReviewDto.builder()
                .id(TestConstants.ID_2)
                .build();
        ReviewDto reviewDto_3 = ReviewDto.builder()
                .id(TestConstants.ID_3)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        Page<Reviews> reviews = new PageImpl<Reviews>(Arrays.asList(review_1, review_2, review_3));
        PageDto<ReviewDto> expected = PageDto.<ReviewDto> builder()
                .items(Arrays.asList(reviewDto_1, reviewDto_2, reviewDto_3))
                .pages(1)
                .build();
        given(reviewsRepository.findAllBySelfNameOrderByIdDesc(TestConstants.USERNAME_1, PAGEABLE)).willReturn(reviews);
        given(mapper.fromEntity(review_1)).willReturn(reviewDto_1);
        given(mapper.fromEntity(review_2)).willReturn(reviewDto_2);
        given(mapper.fromEntity(review_3)).willReturn(reviewDto_3);

        // When
        PageDto<ReviewDto> response = reviewService.list(PAGEABLE, TestConstants.USERNAME_1);


        // Then
        assertThat(response)
                .isNotNull()
                .isInstanceOf(PageDto.class)
                .isEqualTo(expected);

        verify(reviewsRepository, times(1))
                .findAllBySelfNameOrderByIdDesc(TestConstants.USERNAME_1, PAGEABLE);
        verify(reviewsRepository, times(1))
                .findAllBySelfNameOrderByIdDesc(any(), any(Pageable.class));

        verify(mapper, times(1)).fromEntity(review_1);
        verify(mapper, times(1)).fromEntity(review_2);
        verify(mapper, times(1)).fromEntity(review_3);
        verify(mapper, times(3)).fromEntity(any(Reviews.class));
    }

    @Test
    public void get_shouldThrow() {
        // Given
        given(reviewsRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Reviews> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> reviewService.get(TestConstants.ID_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.REVIEWS,
                                               TestConstants.ID_1));
    }

    @Test
    public void get_shouldReturn() {
        // Given
        Reviews review = Reviews.builder()
                .id(TestConstants.ID_1)
                .build();
        ReviewDto reviewDto = ReviewDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(reviewsRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Reviews> of(review));
        given(mapper.fromEntity(review)).willReturn(reviewDto);

        // When
        ReviewDto response = reviewService.get(TestConstants.ID_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(reviewDto);

        verify(reviewsRepository, times(1)).findById(TestConstants.ID_1);
        verify(reviewsRepository, times(1)).findById(any());

        verify(mapper, times(1)).fromEntity(review);
        verify(mapper, times(1)).fromEntity(any(Reviews.class));
    }

    @Test
    public void save_shouldReturn() {
        // Given
        Reviews review = Reviews.builder()
                .id(TestConstants.ID_1)
                .build();
        ReviewDto reviewDto = ReviewDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(reviewsRepository.save(review)).willReturn(review);
        given(mapper.fromEntity(review)).willReturn(reviewDto);
        given(mapper.toEntity(reviewDto)).willReturn(review);

        // When
        ReviewDto response = reviewService.save(reviewDto);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(reviewDto);

        verify(reviewsRepository, times(1)).save(review);
        verify(reviewsRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(review);
        verify(mapper, times(1)).fromEntity(any(Reviews.class));

        verify(mapper, times(1)).toEntity(reviewDto);
        verify(mapper, times(1)).toEntity(any(ReviewDto.class));
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        ReviewDto review = ReviewDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(reviewsRepository).deleteById(TestConstants.ID_1);

        // When
        reviewService.delete(review);

        // Then
        verify(reviewsRepository, times(1)).deleteById(TestConstants.ID_1);
        verify(reviewsRepository, times(1)).deleteById(any());
    }
}