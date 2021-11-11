package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.*;
import lombok.Synchronized;
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
class HaircutOrderRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HaircutOrderRepository haircutOrderRepository;
    @Autowired
    private HaircutRepository haircutRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    @Synchronized
    void findAllByClientNameOrderByCreatedAtDesc_shouldReturnCorrectList() {
        // Given
        User user = User.builder()
                .role(Role.ADMIN)
                .username("be")
                .password("#pswd")
                .email("be@be.be")
                .isActive(true)
                .build();
        user = userRepository.saveAndFlush(user);
        Haircut haircut_1 = Haircut.builder()
                .vendor(user)
                .photo("photo")
                .build();
        Haircut haircut_2 = Haircut.builder()
                .vendor(user)
                .photo("photo")
                .build();
        Appointment appointment_1 = Appointment.builder()
                .client(user)
                .build();
        Appointment appointment_2 = Appointment.builder()
                .client(user)
                .build();
        HaircutOrder haircutOrder_1 = HaircutOrder.builder()
                .appointment(appointment_1)
                .appointment(appointment_2)
                .description("description")
                .client(user)
                .build();
        HaircutOrder haircutOrder_2 = HaircutOrder.builder()
                .appointment(appointment_1)
                .description("description")
                .client(user)
                .build();
        HaircutOrder haircutOrder_3 = HaircutOrder.builder()
                .appointment(appointment_2)
                .description("description")
                .client(user)
                .build();

        haircutRepository.saveAndFlush(haircut_1);
        haircutRepository.saveAndFlush(haircut_2);
        appointmentRepository.saveAndFlush(appointment_1);
        appointmentRepository.saveAndFlush(appointment_2);
        haircutOrderRepository.saveAndFlush(haircutOrder_1);
        haircutOrderRepository.saveAndFlush(haircutOrder_2);
        HaircutOrder ho = haircutOrderRepository.saveAndFlush(haircutOrder_3);

        // When
        Page<HaircutOrder> response = haircutOrderRepository
                .findAllByClientNameOrderByIdDesc("be", PageRequest.of(0, 10));

        // Then
        assertThat(response)
                .isNotNull()
                .hasSize(3)
                .containsExactly(haircutOrder_3, haircutOrder_2, haircutOrder_1);
    }

    @Test
    @Synchronized
    void findAllByClientNameOrderByCreatedAtDesc_shouldReturnEmptyList_ifTableIsEmpty() {
        // Given

        // When
        Page<HaircutOrder> response = haircutOrderRepository
                .findAllByClientNameOrderByIdDesc("be", PageRequest.of(0, 10));

        // Then
        assertThat(response)
                .isNotNull()
                .hasSize(0);
    }
}