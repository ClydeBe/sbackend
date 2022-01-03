package com.thewheel.sawatu.core.service;

import com.thewheel.sawatu.core.service.interfaces.UserService;
import com.thewheel.sawatu.Role;
import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AppUserDetailsServiceTest {

    @InjectMocks
    private AppUserDetailsService service;

    @Mock
    private UserService userService;

    @Test
    public void loadUserByUsername() {
        // Given
        UserDto userDto = UserDto.builder()
                .username(TestConstants.USERNAME_1)
                .role(Role.USER)
                .email(TestConstants.EMAIL_1)
                .isActive(true)
                .password(TestConstants.PASSWORD_1)
                .build();
        User expected = new User(TestConstants.USERNAME_1, TestConstants.PASSWORD_1, Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + TestConstants.ROLE_USER.name())));

        given(userService.getUser(TestConstants.USERNAME_1)).willReturn(userDto);

        // When
        UserDetails response = service.loadUserByUsername(TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
    }
}