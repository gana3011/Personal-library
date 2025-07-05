package com.example.graphqlex.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final String secret;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public Long refreshExpiration = 1000L * 60 * 60 * 24 * 14;
    public Long accessExpiration = 1000L * 60 * 20;

    private Key secretKey;

    @PostConstruct
    public void initKey() {
        secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }


    private Key getSecretKey() {
        return secretKey;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractUserId(String token){return extractAllClaims(token).getSubject();}

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration(); // exp = expiration
    }

    private boolean isExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(String email){
        return createAccessToken(email);
    }

    private String createAccessToken(String email){
        return Jwts.builder().
                subject(email).
                issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis()+ accessExpiration)).
                signWith(getSecretKey()).
                claim("type","access").
                compact();
    }

    public String generateRefreshToken(String id){
        return createRefreshToken(id);
    }

    private String createRefreshToken(String id) {
        String jti = UUID.randomUUID().toString();
        return Jwts.builder()
                .subject(id)
                .id(jti)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .claim("type", "refresh")
                .signWith(getSecretKey())
                .compact();
    }

    public boolean verifyAccessToken(String jwt, UserDetails userDetails){
        String email = extractEmail(jwt);
        return (email.equals(userDetails.getUsername()) && !isExpired(jwt));
    }

    public boolean verifyRefreshToken(String jwt, String userId){
        String id = extractUserId(jwt);
        return (id.equals(userId) && !isExpired(jwt));
    }

    public boolean isAccessToken(String token) {
            Claims claims = extractAllClaims(token);
            return "access".equals(claims.get("type"));
    }

    public boolean isRefreshToken(String token) {
        Claims claims = extractAllClaims(token);
        return "refresh".equals(claims.get("type"));
    }
}


