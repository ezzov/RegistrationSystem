package com.example.registrationsystem.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.registrationsystem.models.User;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.ZoneId;
import java.util.Date;

import static java.time.LocalDateTime.now;

/**
 * JwtProvider for security properties
 */
@Slf4j
@Component
public class JwtProvider {
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final SecurityProperties securityProperties;

    /**
     * Class constructor initialising properties
     */
    @Autowired
    public JwtProvider(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityProperties.getAccessSecret()));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityProperties.getRefreshSecret()));
    }

    /**
     * Method that generates access token
     *
     * @param subject {@link String}
     */
    public String generateAccessToken(String subject) {
        Date tokenTimeOut = Date.from(now().plusMinutes(securityProperties.getAccessTokenTimeoutInMinutes())
                .atZone(ZoneId.systemDefault()).toInstant());
        log.info("Return access token");
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(tokenTimeOut)
                .signWith(jwtAccessSecret)
                .compact();
    }

    /**
     * Method that generates refresh token
     *
     * @param subject {@link String}
     */
    public String generateRefreshToken(String subject) {
        Date tokenTimeOut = Date.from(now().plusMinutes(securityProperties.getRefreshTokenTimeoutInMinutes())
                .atZone(ZoneId.systemDefault()).toInstant());
        log.info("Return refresh token");
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(tokenTimeOut)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    /**
     * Method that validates AccessToken.
     * @param accessToken AccessToken for subsequent requests
     * @return {@link Boolean} true if it can parse claims Jws, otherwise false
     */
    public boolean validateAccessToken(String accessToken) {
        log.info("Return true if access token is valid");
        return validateToken(accessToken, jwtAccessSecret);
    }

    /**
     * Method that validates Token.
     * @param token Token
     * @param secret {@link Key}
     * @return {@link Boolean} true if it can parse claims Jws, otherwise false
     */
    private boolean validateToken(String token, Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            log.info("Return true if can parse claims Jws");
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sigEx) {
            log.error("Invalid signature", sigEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    /**
     * Method that gets user id from token
     * @param token Access Token
     * @return userId from {@link User}
     */
    public long getUserIdFromToken(String token) {
        log.info("Parse access token to userId");
        String userId = Jwts.parserBuilder()
                .setSigningKey(jwtAccessSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        log.info("Return userId");
        return Long.parseLong(userId);
    }
}
