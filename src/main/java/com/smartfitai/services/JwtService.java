package com.smartfitai.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.enterprise.context.ApplicationScoped;
import javax.annotation.PostConstruct;
import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import java.security.Key;
import java.util.Date;

@ApplicationScoped
public class JwtService {

    private String secretKey;
    private Long expirationTime;
    private String issuer;

    private Key key;

    @PostConstruct
    public void init() {
        // load jwt config from config.yaml
        ConfigurationUtil config = ConfigurationUtil.getInstance();
        this.secretKey = config.get("jwt.secret")
                .orElseThrow(() -> new RuntimeException("jwt.secret must be configured in config.yaml"));
        this.expirationTime = config.get("jwt.expiration").map(Long::valueOf).orElse(86400000L);
        this.issuer = config.get("jwt.issuer").orElse("smartfit-ai");

        // make a key from the secret string
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // make a jwt token for a user
    public String generateToken(Long userId, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setIssuer(issuer)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // get user id from the token
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    // get email from the token
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("email", String.class);
    }

    // check if the token is valid (not expired, signature ok)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // check if token is expired
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }

    // get all claims from the token
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
