package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.ProductOrder;
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
@RunWith(SpringRunner.class)
@DirtiesContext
public class ProductOrderRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Test
    public void findAllByUserNameOrderByIdDesc_shouldReturnEmptyList_ifTableIsEmpty() {
        // Given

        // When
        Page<ProductOrder> orders =
                productOrderRepository.findAllByUserNameOrderByIdDesc("be", PageRequest.of(0, 3));

        // Then
        assertThat(orders)
                .isNotNull()
                .hasSize(0);
    }

    @Test
    public void findAllByUserNameOrderByIdDesc_shouldReturnCorrectList() {
        // Given
        User user = User.builder()
                .role(Role.ADMIN)
                .username("be")
                .password("#pswd")
                .email("be@be.be")
                .isActive(true)
                .build();
        user = userRepository.saveAndFlush(user);
        ProductOrder order_1 = ProductOrder.builder()
                .user(user)
                .items("items 1")
                .build();
        ProductOrder order_2 = ProductOrder.builder()
                .user(user)
                .items("items 2")
                .build();

        productOrderRepository.saveAndFlush(order_1);
        productOrderRepository.saveAndFlush(order_2);

        // When
        Page<ProductOrder> orders =
                productOrderRepository.findAllByUserNameOrderByIdDesc("be", PageRequest.of(0, 2));

        // Then
        assertThat(orders)
                .isNotNull()
                .hasSize(2)
                .containsExactly(order_2, order_1);
    }
}