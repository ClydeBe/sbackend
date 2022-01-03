//package com.thewheel.sawatu.database.repository;
//
//
//import com.thewheel.sawatu.Role;
//import com.thewheel.sawatu.database.model.Statistic;
//import com.thewheel.sawatu.database.model.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@DirtiesContext
//public class StatisticRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private StatisticsRepository statisticsRepository;
//
//    @Test
//    public void findFirstByUserNameOrderByIdDesc_shouldReturnAnEmptyOptional() {
//        // Given
//
//        // When
//        Optional<Statistic> statistics = statisticsRepository.findFirstByUserNameOrderByIdDesc("be");
//
//        // Then
//        assertThat(statistics).isEmpty();
//    }
//
//    @Test
//    public void findFirstByUserNameOrderByIdDesc_shouldReturnCorrectOptional() {
//        // Given
//        User user = User.builder()
//                .role(Role.ADMIN)
//                .username("be")
//                .password("#pswd")
//                .email("be@be.be")
//                .isActive(true)
//                .build();
//        Statistic statistic = Statistic.builder()
//                .followersCount(15)
//                .user(user)
//                .rate(4)
//                .build();
//        userRepository.saveAndFlush(user);
//        statisticsRepository.saveAndFlush(statistic);
//
//        // When
//        Optional<Statistic> response = statisticsRepository.findFirstByUserNameOrderByIdDesc("be");
//
//        // Then
//        assertThat(response)
//                .isNotEmpty()
//                .containsSame(statistic);
//    }
//
//}