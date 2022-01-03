package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Reviews;
import com.thewheel.sawatu.Role;
import com.thewheel.sawatu.database.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext
@RunWith(SpringRunner.class)
public class ReviewsRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Test
    public void findAllBySelfNameOrderByIdDesc_shouldReturnEmptyList_ifTableIsEmpty() {
        // Given

        // When
        Page<Reviews> reviews =
                reviewsRepository.findAllBySelfNameOrderByIdDesc("be", PageRequest.of(0, 3));

        // Then
        assertThat(reviews)
                .isNotNull()
                .hasSize(0);
    }

    @Test
    public void findAllBySelfNameOrderByIdDesc_shouldReturnCorrectList() {
        // Given
        User user_1 = User.builder()
                .role(Role.ADMIN)
                .username("be")
                .password("#pswd")
                .email("be@be.be")
                .isActive(true)
                .build();
        User user_2 = User.builder()
                .role(Role.ADMIN)
                .username("me")
                .password("#pswd")
                .email("me@be.me")
                .isActive(true)
                .build();
        User user_3 = User.builder()
                .role(Role.ADMIN)
                .username("te")
                .password("#pswd")
                .email("te@be.te")
                .isActive(true)
                .build();
        Reviews reviews_1 = Reviews.builder()
                .comment("comment")
                .rating(4)
                .reviewer(user_1)
                .self(user_2)
                .build();
        Reviews reviews_2 = Reviews.builder()
                .comment("comment")
                .rating(4)
                .reviewer(user_3)
                .self(user_2)
                .build();
        Reviews reviews_3 = Reviews.builder()
                .comment("comment")
                .rating(4)
                .reviewer(user_3)
                .self(user_1)
                .build();

        userRepository.saveAndFlush(user_1);
        userRepository.saveAndFlush(user_2);
        userRepository.saveAndFlush(user_3);
        reviewsRepository.saveAndFlush(reviews_1);
        reviewsRepository.saveAndFlush(reviews_2);
        reviewsRepository.saveAndFlush(reviews_3);

        // When
        Page<Reviews> reviews =
                reviewsRepository.findAllBySelfNameOrderByIdDesc("me", PageRequest.of(0, 3));

        // Then
        assertThat(reviews)
                .isNotNull()
                .hasSize(2)
                .containsExactly(reviews_2, reviews_1);
    }
}