package com.thewheel.sawatu.auth.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thewheel.sawatu.auth.security.SecurityConstantsBean;
import com.thewheel.sawatu.auth.security.SignatureFactory;
import com.thewheel.sawatu.constants.Constants;
import com.thewheel.sawatu.shared.dto.user.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final SignatureFactory signatureFactory;
    private final SecurityConstantsBean constants;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginDto credentials = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
            Authentication token = new UsernamePasswordAuthenticationToken(credentials.getUsername(),
                                                                           credentials.getPassword());
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC512(
                signatureFactory.getSignature(userDetails.getUsername()).getBytes(StandardCharsets.UTF_8));
        String accessToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer(request.getRequestURL().toString())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(
                        System.currentTimeMillis() + constants.getAccessTokenDuration() * 60 * 1000L))
                .withClaim(Constants.TOKEN_GRANTED_AUTHORITY_NAME,
                           userDetails.getAuthorities()
                                   .stream()
                                   .map(GrantedAuthority::getAuthority)
                                   .collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer(request.getRequestURL().toString())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(
                        System.currentTimeMillis() + constants.getRefreshTokenDuration() * 60 * 1000L))
                .sign(algorithm);
        response.setContentType(APPLICATION_JSON_VALUE);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}

