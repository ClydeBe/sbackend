package com.thewheel.sawatu.core.service;

import com.thewheel.sawatu.database.repository.UserRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) {
        com.thewheel.sawatu.database.model.User user = userRepository.findByUsernameOrEmail(s).orElseThrow(
                () -> new EntityNotFoundException(String.format(MessageConstants.USER_NOT_FOUND, s)));
        String role = user.getRole().name();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
