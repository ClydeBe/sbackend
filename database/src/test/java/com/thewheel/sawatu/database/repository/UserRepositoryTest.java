package com.thewheel.sawatu.database.repository;


import com.thewheel.sawatu.Role;
import com.thewheel.sawatu.database.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DirtiesContext
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsernameOrEmail_shouldReturnAnEmptyOptional() {
        // Given

        // When
        Optional<User> response = userRepository.findByUsernameOrEmail("be");

        // Then
        assertThat(response).isEmpty();
    }

    @Test
    public void findByUsernameOrEmail_shouldReturnCorrectOptional_ifEmailOrUsernameIsUsedAsParam() {
        // Given
        User user = User.builder()
                .role(Role.ADMIN)
                .username("be")
                .password("#pswd")
                .email("be@be.be")
                .isActive(true)
                .build();
        user = userRepository.saveAndFlush(user);

        // When
        Optional<User> response1 = userRepository.findByUsernameOrEmail("be");
        Optional<User> response2 = userRepository.findByUsernameOrEmail("be@be.be");

        // Then
        assertThat(response1)
                .isNotEmpty()
                .containsSame(user);
        assertThat(response2)
                .isNotEmpty()
                .containsSame(user);
    }

    @Test
    public void saveUserRole_shouldReturn() {
        // Given
        User user = User.builder()
                .role(Role.ADMIN)
                .username("be")
                .password("#pswd")
                .email("be@be.be")
                .isActive(true)
                .build();
        user = userRepository.saveAndFlush(user);
        user.setRole(Role.USER);

        // When
        userRepository.saveUserRole("be", Role.USER);
        User response = userRepository.findByUsernameOrEmail("be").get();

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    public void activateAccount_shouldReturn() {
        // Given
        User user = User.builder()
                .role(Role.ADMIN)
                .username("be")
                .password("#pswd")
                .email("be@be.be")
                .isActive(false)
                .build();
        user = userRepository.saveAndFlush(user);
        user.setIsActive(true);

        // When
        userRepository.activateAccount("be");
        User response = userRepository.findByUsernameOrEmail("be").get();

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(user);
    }
}