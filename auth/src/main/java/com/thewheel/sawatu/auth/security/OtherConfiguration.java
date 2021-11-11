package com.thewheel.sawatu.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtherConfiguration {

    @Bean
    public SecurityConstantsBean SecurityConstants() {
        return new SecurityConstantsBean();
    }

}
