package com.thewheel.sawatu.shared.config;

import com.thewheel.sawatu.database.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@PropertySources(
        value = {
                @PropertySource("classpath:application.yml"),
                @PropertySource("classpath:tokenSignatures.properties")

        })
@Slf4j
public class GeneralConfiguration {

    @Value("${spring.datasource.hikari.schema}")
    private String schema;

    private String dictionaryLanguage = "french";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<User> auditorAware() {
        return new AppAuditAware();
    }

}
