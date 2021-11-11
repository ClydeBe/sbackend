package com.thewheel.sawatu.auth.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thewheel.sawatu.auth.security.SignatureFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.thewheel.sawatu.shared.constant.Constants.*;

public class AppAuthorizationFilter extends OncePerRequestFilter {
    private final SignatureFactory signatureFactory;

    public AppAuthorizationFilter(final SignatureFactory factory) {
        signatureFactory = factory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("====================== INSIDE FILTER =========================");
        String authHeader = request.getHeader("Authorization");
        String path = request.getServletPath();

        if(PUBLIC_ENDPOINTS_EXACT.contains(path) ||
           PUBLIC_ENDPOINTS_PATTERN.stream().anyMatch(path::contains)) {
            filterChain.doFilter(request, response);
        } else if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("====================== INSIDE LONG =========================");

            try {
                String token = authHeader.substring(7);
                String subject = JWT.decode(token).getSubject();
                Algorithm algorithm = Algorithm.HMAC512(signatureFactory.getSignature(subject));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedToken = verifier.verify(token);
                String username = decodedToken.getSubject();
                String role = decodedToken.getClaim(TOKEN_GRANTED_AUTHORITY_NAME).asString();
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.singleton(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(request, response);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        } else {
            throw new AccessDeniedException("No authorization token found");
        }
    }
}
