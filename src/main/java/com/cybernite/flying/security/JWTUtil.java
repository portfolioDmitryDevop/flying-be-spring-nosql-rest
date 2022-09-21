package com.cybernite.flying.security;

import com.cybernite.flying.dto.AuthToken;
import com.cybernite.flying.dto.LoginData;
import com.cybernite.flying.entities.Account;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class JWTUtil {
    @Value("${app.jwt.exp.period}")
    long expTimePeriodMs;
    @Value("${app.jwt.secret}")
    String secret;

    public String create(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(getExpDate())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private Date getExpDate() {
        return new Date(new Date().getTime() + expTimePeriodMs);
    }

    public String validate(String jwt) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            log.error("token expired");
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            log.error(e.toString());
        } catch (SignatureException e) {
            log.error("courrupted token");
        } catch (IllegalArgumentException e) {
            log.error("empty token");
        }
        return null;
    }

    public AuthToken getToken(LoginData loginData, Account account) {
        String accessToken = "Bearer " + create(loginData.getEmail());
        return new AuthToken(accessToken, account.getRole().replace("ROLE_", ""));
    }
}
