package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.database.model.Profile;
import com.thewheel.sawatu.database.repository.ProfileRepository;
import com.thewheel.sawatu.shared.dto.ProfileDto;
import com.thewheel.sawatu.shared.exception.BadRequestException;
import com.thewheel.sawatu.core.service.interfaces.UserService;
import com.thewheel.sawatu.Role;
import com.thewheel.sawatu.database.model.User;
import com.thewheel.sawatu.database.repository.UserRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final Mapper mapper;

    @Override
    public UserDto create(UserDto user) {
        return mapper.fromEntity(userRepository.save(mapper.toEntity(user)));
    }

    @Override
    public UserDto merge(UserDto user) {
        User persistentUser = userRepository.findByUsernameOrEmail(user.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(MessageConstants.USER_NOT_FOUND, user.getUsername())));
        User tmp = mapper.toEntity(user);
        tmp.setPassword(persistentUser.getPassword());
        tmp.setRole(persistentUser.getRole());
        tmp = userRepository.save(tmp);
        return mapper.fromEntity(tmp);
    }

    @Override
    public void saveUserRole(String username, Role role) {
        userRepository.saveUserRole(username, role);
    }

    @Override
    @Transactional
    public void activateAccount(String username) {
        userRepository.activateAccount(username);
    }

    @Override
    public UserDto getUser(String username) {
        User user = userRepository.findByUsernameOrEmail(username).orElseThrow(
                () -> new EntityNotFoundException(String.format(MessageConstants.USER_NOT_FOUND, username)));
        return mapper.fromEntity(user);
    }

    @Override
    public ProfileDto getProfile(String username) {
        Profile profile = profileRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format(MessageConstants.USER_NOT_FOUND, username)));
        return mapper.fromEntity(profile);
    }

    @Override
    public PageDto<UserDto> getAll(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return PageDto.<UserDto> builder()
                .items(list.stream()
                               .map(mapper::fromEntity)
                               .collect(Collectors.toList()))
                .pages(list.getTotalPages())
                .build();
    }

    @Override
    public User fromDtoToUser(UserDto userDto, boolean isNewUser) throws BadRequestException {
        User user = mapper.toEntity(userDto);
        if (isNewUser && userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(Role.USER);
            user.setIsActive(false);
        } else throw new BadRequestException(MessageConstants.NO_PASSWORD_FOUND);
        return user;
    }
}
