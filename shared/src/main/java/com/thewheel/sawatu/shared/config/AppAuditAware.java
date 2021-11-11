package com.thewheel.sawatu.shared.config;

import com.thewheel.sawatu.database.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AppAuditAware implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username == null) return Optional.empty();
        return Optional.of(User.builder().username(username).build());
    }
}
