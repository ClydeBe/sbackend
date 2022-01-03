package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.Role;
import com.thewheel.sawatu.database.model.User;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProfileDto;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import com.thewheel.sawatu.shared.exception.BadRequestException;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto create(UserDto user);
    UserDto merge(UserDto user);
    void saveUserRole(String username, Role role);
    void activateAccount(String username);
    UserDto getUser(String username);
    ProfileDto getProfile(String username);
    PageDto<UserDto> getAll(Pageable pageable);

    public User fromDtoToUser(UserDto userDto, boolean isNewUser) throws BadRequestException;
}
