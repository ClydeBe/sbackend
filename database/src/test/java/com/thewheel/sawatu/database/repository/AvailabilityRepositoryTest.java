package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Availability;
import com.thewheel.sawatu.Role;
import com.thewheel.sawatu.database.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

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

        Availability availability = Availability.builder()
                .availabilities("this is availabilities")
                .user(user)
                .build();

        availabilityRepository.saveAndFlush(availability);

        // When
        Optional<Availability> response = availabilityRepository.findFirstByUserName("be");

        // Then
        assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsSame(availability);
    }

    @Test
    void findFirstByUserName_shouldReturnEmptyOptional_IfTableInDatabaseIsEmpty() {
        // Given

        // When
        Optional<Availability> response = availabilityRepository.findFirstByUserName("be");

        // Then
        assertThat(response)
                .isNotNull()
                .isEmpty();
    }

}