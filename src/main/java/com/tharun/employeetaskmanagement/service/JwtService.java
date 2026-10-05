package com.tharun.employeetaskmanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import java.time.Instant;

/*
 * Service responsible for generating JWT access tokens.
 */
@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /*
     * Generates a JWT containing the user's identity and role.
     */
    public String generateToken(
            Long userId,
            String email,
            String role) {

        Instant now = Instant.now();

        /*
         * Calculate when the token should expire.
         */
        Instant expiration = now.plusMillis(jwtExpiration);

        /*
         * Create the information stored inside the JWT.
         */
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiresAt(expiration)
                .claim("email", email)
                .claim("role", role)
                .build();

        /*
         * Create the JWT header using HS256.
         */
        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        /*
         * Sign and generate the final JWT string.
         */
        return jwtEncoder
                .encode(JwtEncoderParameters.from(header, claims))
                .getTokenValue();
    }
}