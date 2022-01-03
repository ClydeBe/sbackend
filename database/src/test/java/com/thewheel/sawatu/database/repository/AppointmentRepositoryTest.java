package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.database.model.Appointment;
import com.thewheel.sawatu.database.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
class AppointmentRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void listByUsernameOrderByIdDesc_ShouldReturnCorrectList() {
        // Given
        User user = User.builder()
                .username("be")
                .email("be@be.be")
                .password("#psdr")
                .role(TestConstants.ROLE_ADMIN)
                .isActive(true)
                .build();
        user = userRepository.saveAndFlush(user);

        Appointment appointment_1 = Appointment.builder()
                .client(user)
                .dateTime(LocalDateTime.now())
                .build();
        Appointment appointment_2 = Appointment.builder()
                .client(user)
                .dateTime(LocalDateTime.now())
                .build();
        Appointment appointment_3 = Appointment.builder()
                .client(user)
                .dateTime(LocalDateTime.now())
                .build();

        appointmentRepository.saveAndFlush(appointment_1);
        appointmentRepository.saveAndFlush(appointment_2);
        appointmentRepository.saveAndFlush(appointment_3);

        // When
        Page<Appointment> response =
                appointmentRepository.findAllByClientNameOrderByIdDesc("be", PageRequest.of(0, 10));

        // Then
        assertThat(response)
                .isNotNull()
                .hasSize(3)
                .containsExactly(appointment_3, appointment_2, appointment_1);
    }

    @Test
    void findAllByVendorNameOrderByIdDesc_ShouldReturnCorrectList() {
        // Given
        User user = User.builder()
                .username("be")
                .email("be@be.be")
                .password("#psdr")
                .role(TestConstants.ROLE_ADMIN)
                .isActive(true)
                .build();
        user = userRepository.saveAndFlush(user);

        Appointment appointment_1 = Appointment.builder()
                .vendor(user)
                .dateTime(LocalDateTime.now())
                .build();
        Appointment appointment_2 = Appointment.builder()
                .vendor(user)
                .dateTime(LocalDateTime.now())
                .build();
        Appointment appointment_3 = Appointment.builder()
                .vendor(user)
                .dateTime(LocalDateTime.now())
                .build();

        appointmentRepository.saveAndFlush(appointment_1);
        appointmentRepository.saveAndFlush(appointment_2);
        appointmentRepository.saveAndFlush(appointment_3);

        // When
        Page<Appointment> app = appointmentRepository.findAllByVendorNameOrderByIdDesc("be", PageRequest.of(0, 10));

        // Then
        assertThat(app)
                .isNotNull()
                .hasSize(3)
                .containsExactly(appointment_3, appointment_2, appointment_1);
    }

    @Test
    void findAllByClientNameOrderByIdDesc_ShouldReturnEmptyList_ifTableIsEmptyInDatabase() {
        // Given

        // When
        Page<Appointment> response = appointmentRepository.findAllByClientNameOrderByIdDesc("be",
                                                                                            PageRequest.of(0, 10));
        // Then
        assertThat(response)
                .isNotNull()
                .hasSize(0);
    }

    @Test
    void findAllByVendorNameOrderByIdDesc_ShouldReturnEmptyList_ifTableIsEmptyInDatabase() {
        // Given

        // When
        Page<Appointment> response = appointmentRepository.findAllByVendorNameOrderByIdDesc("be",
                                                                                            PageRequest.of(0, 10));
        // Then
        assertThat(response)
                .isNotNull()
                .hasSize(0);
    }
}