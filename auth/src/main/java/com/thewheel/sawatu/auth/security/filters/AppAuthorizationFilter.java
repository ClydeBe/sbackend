package com.thewheel.sawatu.auth.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thewheel.sawatu.auth.security.SignatureFactory;
import com.thewheel.sawatu.shared.exception.BadRequestException;
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

import static com.thewheel.sawatu.constants.Constants.TOKEN_GRANTED_AUTHORITY_NAME;

public class AppAuthorizationFilter extends OncePerRequestFilter {
    private final SignatureFactory signatureFactory;

    public AppAuthorizationFilter(final SignatureFactory factory) {
        signatureFactory = factory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String path = request.getServletPath();

        if(authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String subject = JWT.decode(token).getSubject();
                Algorithm algorithm = Algorithm.HMAC512(signatureFactory.getSignature(subject));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedToken = verifier.verify(token);
                String username = decodedToken.getSubject();
                String role = decodedToken.getClaim(TOKEN_GRANTED_AUTHORITY_NAME).asArray(String.class)[0];
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.singleton(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(request, response);
            } catch (Exception exception) {
                throw new BadRequestException(exception.getMessage());
            }
        }
    }
}
