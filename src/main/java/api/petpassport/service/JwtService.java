package api.petpassport.service;

import api.petpassport.config.security.SecurityConstants;
import api.petpassport.config.security.dto.JwtDto;
import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.exception.ForbiddenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

public class JwtService {

    public static JwtDto buildToken(String jwtSecret, Long validSecond, UUID userId) {
        String accessToken = Jwts.builder()
                .claim(SecurityConstants.JWT_USER_ID_FIELD, userId)
                .setExpiration(new Date(System.currentTimeMillis() + validSecond))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return new JwtDto(accessToken);
    }

    public static PrincipalDto parseToken(String jwtSecret, String token) {
        Jws<Claims> jwt;
        try {
            jwt = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
        } catch (RuntimeException exc) {
            throw new ForbiddenException("Error parse token!");
        }

        Date exp = jwt.getBody().getExpiration();
        Date today = new Date();
        if (today.after(exp)) throw new ForbiddenException("Token is overdue!");

        String userId = jwt.getBody().get(SecurityConstants.JWT_USER_ID_FIELD).toString();
        return new PrincipalDto(UUID.fromString(userId), UUID.randomUUID().toString());
    }

}
