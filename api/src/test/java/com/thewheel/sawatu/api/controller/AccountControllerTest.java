package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.auth.security.SignatureFactory;
import com.thewheel.sawatu.auth.security.token.TokenFactory;
import com.thewheel.sawatu.shared.exception.BadRequestException;
import com.thewheel.sawatu.core.mailing.EmailService;
import com.thewheel.sawatu.core.service.interfaces.UserService;
import com.thewheel.sawatu.database.model.User;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController controller;

    @Mock
    private UserService userService;

    @Mock
    private SignatureFactory signatureFactory;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private EmailService emailService;

    @Mock
    private TokenFactory tokenFactory;

    @Mock
    private Mapper mapper;

    @Test
    public void getUserById_shouldReturn() {
        // Given
        UserDto user = UserDto.builder()
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .isActive(true)
                .role(TestConstants.ROLE_USER)
                .password(TestConstants.PASSWORD_1)
                .build();
        given(userService.getUser(TestConstants.USERNAME_1)).willReturn(user);

        // When
        UserDto response = controller.getUserById(TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(user);
        verify(userService, times(1)).getUser(TestConstants.USERNAME_1);
        verify(userService, times(1)).getUser(any());
    }

    @Test
    public void updateUser_shouldReturn() {
        // Given
        UserDto user = UserDto.builder()
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .isActive(true)
                .role(TestConstants.ROLE_USER)
                .password(TestConstants.PASSWORD_1)
                .build();
        given(userService.merge(user)).willReturn(user);

        // When
        UserDto response = controller.updateUser(user);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(user);
        verify(userService, times(1)).merge(user);
        verify(userService, times(1)).merge(any());
    }

    @Test
    public void createUser_shouldReturn() {
        // Given
        UserDto userDto = UserDto.builder()
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .isActive(true)
                .role(TestConstants.ROLE_USER)
                .password(TestConstants.PASSWORD_1)
                .build();
        User user = User.builder()
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .isActive(true)
                .role(TestConstants.ROLE_USER)
                .password(TestConstants.PASSWORD_1)
                .build();
        given(userService.fromDtoToUser(userDto, true)).willReturn(user);
        given(mapper.fromEntity(user)).willReturn(userDto);
        given(tokenFactory.generateAccountActivationToken(TestConstants.USERNAME_1)).willReturn(
                TestConstants.STRING_CONSTANT_1);
        doNothing().when(emailService).sendActivationMessage(userDto,
                                                             TestConstants.STRING_CONSTANT_1,
                                                             TestConstants.EMAIL_1);
        doNothing().when(emailService).sendWelcomeMessage(TestConstants.USERNAME_1, TestConstants.EMAIL_1);
        given(userService.create(userDto)).willReturn(userDto);

        // When
        UserDto response = controller.createUser(userDto);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(userDto);

        verify(userService, times(1)).fromDtoToUser(userDto, true);
        verify(userService, times(1)).fromDtoToUser(any(), any(Boolean.class));

        verify(mapper, times(1)).fromEntity(user);
        verify(mapper, times(1)).fromEntity(any(User.class));

        verify(userService, times(1)).create(userDto);
        verify(userService, times(1)).create(any());

        verify(emailService, times(1)).sendWelcomeMessage(TestConstants.USERNAME_1,
                                                          TestConstants.EMAIL_1);
        verify(emailService, times(1)).sendWelcomeMessage(any(), any());

        verify(emailService, times(1)).sendActivationMessage(userDto,
                                                             TestConstants.STRING_CONSTANT_1,
                                                             TestConstants.EMAIL_1);
        verify(emailService, times(1)).sendActivationMessage(any(), any(), any());
    }

    @Test
    public void verifyToken_shouldThrow_ifNoTokenIsProvided() {
        // Given
        Optional<String> token = Optional.empty();

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> controller.verifyEmail(token));

        // Then
        response
                .isNotNull()
                .isInstanceOf(BadRequestException.class)
                .withFailMessage(MessageConstants.NO_TOKEN_PROVIDED);

    }

    @Test
    public void verifyToken_shouldThrow_ifTokenIsNotDecoded() {
        // Given
        Optional<String> token = Optional.of(TestConstants.STRING_CONSTANT_1);
        given(tokenFactory.verifyAccountValidationToken(TestConstants.STRING_CONSTANT_1)).willReturn(
                null);

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> controller.verifyEmail(token));

        // Then
        response
                .isNotNull()
                .isInstanceOf(RuntimeException.class)
                .withFailMessage(MessageConstants.INVALID_TOKEN);
        verify(tokenFactory, times(1)).verifyAccountValidationToken(TestConstants.STRING_CONSTANT_1);
        verify(tokenFactory, times(1)).verifyAccountValidationToken(any());
    }

    @Test
    public void verifyToken_shouldReturn() {
        // Given
        Optional<String> token = Optional.of(TestConstants.STRING_CONSTANT_1);
        given(tokenFactory.verifyAccountValidationToken(TestConstants.STRING_CONSTANT_1)).willReturn(
                TestConstants.USERNAME_1);
        doNothing().when(userService).activateAccount(TestConstants.USERNAME_1);

        // When
        String response = controller.verifyEmail(token);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(MessageConstants.ACCOUNT_ACTIVATED);
        verify(tokenFactory, times(1)).verifyAccountValidationToken(TestConstants.STRING_CONSTANT_1);
        verify(tokenFactory, times(1)).verifyAccountValidationToken(any());

        verify(userService, times(1)).activateAccount(TestConstants.USERNAME_1);
        verify(userService, times(1)).activateAccount(any());
    }

    @Test
    public void refreshToken_shouldReturn() {
        // Given

        // When

        // Then
    }

}