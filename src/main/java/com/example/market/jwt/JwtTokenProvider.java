package com.example.market.jwt;

import com.example.market.common.AppProperties;
import com.example.market.security.MyUser;
import com.example.market.security.MyUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final ObjectMapper om;
    private final AppProperties appProperties;
    private final SecretKey secretKey;

    public JwtTokenProvider(ObjectMapper om, AppProperties appProperties) {
        this.om = om;
        this.appProperties = appProperties;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(appProperties.getJwt().getSecret()));
    }

    public String generateAccessToken(MyUser myUser) {
        return generateToken(myUser, appProperties.getJwt().getAccessTokenExpiry());
    }

    public String generateRefreshToken(MyUser myUser) {
        return generateToken(myUser, appProperties.getJwt().getRefreshTokenExpiry());
    }

    private String generateToken(MyUser myUser, long tokenValidMilliSecond) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidMilliSecond))
                .claims(createClaims(myUser))

                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();

    }

    private Claims createClaims(MyUser myUser) {
        try {
            String json = om.writeValueAsString(myUser); // 객체 to JSON
            return Jwts.claims().add("signedUser", json).build(); // Claims에 JSON 저장
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims claims = getAllClaims(token);
            String json = (String)claims.get("signedUser");
            MyUser myUser = om.readValue(json, MyUser.class);
            MyUserDetail myUserDetails = new MyUserDetail();
            myUserDetails.setMyUser(myUser);
            return myUserDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Authentication getAuthentication(String token) {

        UserDetails userDetails = getUserDetailsFromToken(token);
        return userDetails == null
                ? null
                : new UsernamePasswordAuthenticationToken(userDetails
                , null
                , userDetails.getAuthorities()
        );

    }

    public boolean isValidateToken(String token) {

        try {

            return !getAllClaims(token).getExpiration().before(new Date());

        } catch (Exception e) {
            return false;
        }

    }

    public String resolveToken(HttpServletRequest req) {

        // appProperties가 null이 아닌지 체크
        if (appProperties == null || appProperties.getJwt() == null) {
            // 로깅 또는 예외 처리를 고려
            return null;
        }

        // 헤더 스키마 이름과 토큰 타입이 null인지 체크
        String headerSchemaName = appProperties.getJwt().getHeaderSchemaName();
        String tokenType = appProperties.getJwt().getTokenType();

        if (headerSchemaName == null || tokenType == null) {
            // 로깅 또는 예외 처리
            return null;
        }

        // 요청 헤더에서 JWT 토큰 추출
        String jwt = req.getHeader(headerSchemaName);

        // JWT가 null이거나 빈 값인지 체크
        if (jwt == null || jwt.isEmpty()) {
            return null;
        }

        // JWT가 올바른 토큰 타입으로 시작하는지 체크
        if (!jwt.startsWith(tokenType)) {
            return null;
        }

        // 토큰 타입을 제거하고 남은 부분을 반환
        return jwt.substring(tokenType.length()).trim();
    }

}
