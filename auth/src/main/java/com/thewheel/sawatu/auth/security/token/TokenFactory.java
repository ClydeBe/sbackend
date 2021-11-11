package com.thewheel.sawatu.auth.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thewheel.sawatu.auth.security.SignatureFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenFactory {

    private final SignatureFactory signatureFactory;

    public String generateAccountActivationToken(String username) {
        Algorithm algorithm = Algorithm.HMAC512(signatureFactory
                .getSignature(username).getBytes(StandardCharsets.UTF_8));
        return JWT.create()
                .withSubject(username)
                .withIssuer("SAWATU TEAM")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(
                        System.currentTimeMillis() + 60 * 60 * 1000))
                .sign(algorithm);
    }

    public String verifyAccountValidationToken(String token) {
        try {
            String subject = JWT.decode(token).getSubject();
            System.out.println("Hello world subject " + subject);
            Algorithm algorithm = Algorithm.HMAC512(signatureFactory.getSignature(subject));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedToken = verifier.verify(token);
            return subject;
        } catch (Exception exception) {
            return null;
        }
    }

}
