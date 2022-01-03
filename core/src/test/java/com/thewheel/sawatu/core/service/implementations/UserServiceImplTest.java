package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.shared.exception.BadRequestException;
import com.thewheel.sawatu.core.service.interfaces.RoleService;
import com.thewheel.sawatu.database.model.User;
import com.thewheel.sawatu.database.repository.UserRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @Mock
    private Mapper mapper;

    @Test
    public void create_shouldReturn() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .build();
        UserDto dto = UserDto.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .build();
        given(mapper.toEntity(dto)).willReturn(user);
        given(mapper.fromEntity(user)).willReturn(dto);
        given(userRepository.save(user)).willReturn(user);

        // When
        UserDto response = userService.create(dto);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);

        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(user);
        verify(mapper, times(1)).fromEntity(any(User.class));

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(UserDto.class));
    }

    @Test
    public void merge_shouldThrow() {
        // Given
        UserDto dto = UserDto.builder()
                .username(TestConstants.USERNAME_1)
                .build();
        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> empty());


        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> userService.merge(dto));

        // Then
        response
                .isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, TestConstants.USERNAME_1));
        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());

    }

    @Test
    public void merge_shouldReturn() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .build();
        UserDto dto = UserDto.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .build();
        given(mapper.toEntity(dto)).willReturn(user);
        given(mapper.fromEntity(user)).willReturn(dto);
        given(userRepository.save(user)).willReturn(user);
        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));

        // When
        UserDto response = userService.merge(dto);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);

        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(user);
        verify(mapper, times(1)).fromEntity(any(User.class));

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(UserDto.class));

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());
    }

    @Test
    public void saveUserRole_shouldReturn() {
        // Given
        doNothing().when(userRepository).activateAccount(TestConstants.USERNAME_1);

        // When
        userService.activateAccount(TestConstants.USERNAME_1);

        // Then
        verify(userRepository, times(1)).activateAccount(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).activateAccount(any());
    }

    @Test
    public void activateAccount_shouldReturn() {
        // Given
        doNothing().when(userRepository).saveUserRole(TestConstants.USERNAME_1, TestConstants.ROLE_USER);

        // When
        userService.saveUserRole(TestConstants.USERNAME_1, TestConstants.ROLE_USER);

        // Then
        verify(userRepository, times(1)).saveUserRole(TestConstants.USERNAME_1, TestConstants.ROLE_USER);
        verify(userRepository, times(1)).saveUserRole(any(), any());
    }

    @Test
    public void getUser_shouldThrow() {
        // Given
        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> userService.getUser(TestConstants.USERNAME_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, TestConstants.USERNAME_1));

        verify(userRepository).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository).findByUsernameOrEmail(any());
    }

    @Test
    public void getUser_shouldReturn() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .build();
        UserDto dto = UserDto.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .build();
        given(mapper.fromEntity(user)).willReturn(dto);
        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));

        // When
        UserDto response = userService.getUser(TestConstants.USERNAME_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);

        verify(userRepository).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository).findByUsernameOrEmail(any());

        verify(mapper, times(1)).fromEntity(user);
        verify(mapper, times(1)).fromEntity(any(User.class));
    }

    @Test
    public void getAll_shouldReturn() {
        // Given
        User user_1 = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .build();
        UserDto dto_1 = UserDto.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .build();
        User user_2 = User.builder()
                .password(TestConstants.PASSWORD_2)
                .isActive(true)
                .email(TestConstants.EMAIL_2)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_2)
                .build();
        UserDto dto_2 = UserDto.builder()
                .password(TestConstants.PASSWORD_2)
                .isActive(true)
                .email(TestConstants.EMAIL_2)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_2)
                .build();
        Page<User> users = new PageImpl<User>(Arrays.asList(user_1, user_2));
        Pageable PAGEABLE = PageRequest.of(0, 10);
        PageDto<UserDto> expected = PageDto.<UserDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        given(mapper.fromEntity(user_1)).willReturn(dto_1);
        given(mapper.fromEntity(user_2)).willReturn(dto_2);
        given(userRepository.findAll(PAGEABLE)).willReturn(users);

        // When
        PageDto<UserDto> response = userService.getAll(PAGEABLE);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findAll(PAGEABLE);
        verify(userRepository, times(1)).findAll(any(Pageable.class));

        verify(mapper, times(1)).fromEntity(user_1);
        verify(mapper, times(1)).fromEntity(user_2);
        verify(mapper, times(2)).fromEntity(any(User.class));

    }

    @Test
    public void fromDtoToUser_shouldThrow() {
        // Given
        User user = User.builder()
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .build();
        UserDto dto = UserDto.builder()
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .build();
        given(mapper.toEntity(dto)).willReturn(user);

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> userService.fromDtoToUser(dto, true));

        // Then
        response.isNotNull()
                .isInstanceOf(BadRequestException.class)
                .withFailMessage(MessageConstants.NO_PASSWORD_FOUND);

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(UserDto.class));
    }

    @Test
    public void fromDtoToUserShouldReturn() {
        // Given
        User user = User.builder()
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .password(TestConstants.PASSWORD_1)
                .build();
        UserDto dto = UserDto.builder()
                .isActive(true)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .password(TestConstants.PASSWORD_1)
                .build();
        given(mapper.toEntity(dto)).willReturn(user);
        given(passwordEncoder.encode(TestConstants.PASSWORD_1)).willReturn(TestConstants.PASSWORD_1);
        User expected = User.builder()
                .isActive(false)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .username(TestConstants.USERNAME_1)
                .password(TestConstants.PASSWORD_1)
                .build();

        // When
        User response = null;
        try {
            response = userService.fromDtoToUser(dto, true);
        } catch (BadRequestException e) {
            // Cannot happen and if, will throw RuntimeException
            throw new RuntimeException("Text failed");
        }

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(UserDto.class));

        verify(passwordEncoder, times(1)).encode(TestConstants.PASSWORD_1);
        verify(passwordEncoder, times(1)).encode(any());

    }
}