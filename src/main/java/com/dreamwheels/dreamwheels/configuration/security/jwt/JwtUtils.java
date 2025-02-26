package com.dreamwheels.dreamwheels.configuration.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {
    public static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

//    @Value("${application.security.jwt.secret-key}")
    private final String jwtSecret = System.getenv("JWT_SECRET_KEY");

//    @Value("${application.security.jwt.expiration}")
    private final String jwtExpirations = System.getenv("JWT_EXPIRATION");

//    @Value("${application.security.jwt.refresh-token.expiration}")
    private String refreshExpiration = System.getenv("JWT_REFRESH_TOKEN_EXPIRATION");


    public String extractUsername(String token){
        return extraClaim(token, Claims::getSubject);
    }

    private <T>  T extraClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        System.out.println("username in token" + username);
        System.out.println(userDetails.getUsername());
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extraClaim(token, Claims::getExpiration);
    }

    public String generateJwtToken(UserDetails userDetails){
        return generateJwtToken(new HashMap<>(), userDetails);
    }

    private String generateJwtToken(Map<String,Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, Long.parseLong(jwtExpirations));
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long jwtExpirations) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirations))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenRevoked(String token) {
//        extraClaim(token,)
        return false;
    }

    public long getRefreshExpiration() {
        return Long.parseLong(refreshExpiration);
    }

    public void setRefreshExpiration(long refreshExpiration) {
        this.refreshExpiration = String.valueOf(refreshExpiration);
    }
}
