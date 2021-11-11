package com.thewheel.sawatu.auth.security;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("jwt.session")
public class SignatureFactory {

    private List<String> tokens;

    public String getSignature(String username) {
        return tokens.get(Math.abs(username.hashCode()) % tokens.size());
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
}
