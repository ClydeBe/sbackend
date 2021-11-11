package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Availability;
import com.thewheel.sawatu.database.model.Role;
import com.thewheel.sawatu.database.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
class AvailabilityRepositoryTest {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllByUserNameOrderByIdDesc_shouldReturnCorrectList() {
        // Given
        User user = User.builder()
                .username("be")
                .email("be@be.be")
                .isActive(true)
                .password("#pswd")
                .role(Role.ADMIN)
                .build();
        user = userRepository.saveAndFlush(user);

        Availability availability_1 = Availability.builder()
                .availabilities("this is availabilities")
                .user(user)
                .build();

        Availability availability_2 = Availability.builder()
                .availabilities("this is availabilities")
                .user(user)
                .build();

        availabilityRepository.saveAndFlush(availability_1);
        availabilityRepository.saveAndFlush(availability_2);

        // When
        Page<Availability> response = availabilityRepository
                .findAllByUserNameOrderByIdDesc("be", PageRequest.of(0, 10));

        // Then
        assertThat(response)
                .isNotNull()
                .hasSize(2)
                .containsExactly(availability_2, availability_1);
    }

    @Test
    void findAllByUserNameOrderByIdDesc_shouldReturnCorrectList_IfTableInDatabaseIsEmpty() {
        // Given

        // When
        Page<Availability> response = availabilityRepository
                .findAllByUserNameOrderByIdDesc("be", PageRequest.of(0, 10));

        // Then
        assertThat(response)
                .isNotNull()
                .hasSize(0);
    }
}