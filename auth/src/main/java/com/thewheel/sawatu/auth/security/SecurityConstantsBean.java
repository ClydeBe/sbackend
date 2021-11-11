package com.thewheel.sawatu.auth.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("sawatu.session")
public final class SecurityConstantsBean {

    @Value("${access-token-duration-minutes}")
    private int accessTokenDuration;

    @Value("${refresh-token-duration-minutes}")
    private int refreshTokenDuration;

    @Value("${token.issuer}")
    private String issuer;

}
