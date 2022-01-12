package com.bruincreates.server.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtUtil {

    public static final String SECRET = "google-705200057";
    public static final String ISS = "BruinCreates";

    public static String createToken(String username, String role) {

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(1, ChronoUnit.DAYS);

        return Jwts.builder()
                .setIssuer(ISS)
                .setSubject(username)
                .claim("name", username)
                .claim("scope", role)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(SECRET)
                )
                .compact();
    }

    public static String parseToken(String jwtToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(jwtToken)
                .getBody();
        return (String) claims.get("name");
    }
}
