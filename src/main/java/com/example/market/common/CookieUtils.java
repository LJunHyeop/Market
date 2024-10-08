package com.example.market.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieUtils {

    //  요청 Header 에 원하는 Name 쿠키를 찾는 메소드  //
    public Cookie getCookie(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null && cookies.length > 0) {
            for(Cookie cookie : cookies) {
                if(name.equals(cookie.getName())) { return cookie; }
            }
        }
        return null;
    }

    //  요청 Header 에 원하는 Name, Type 쿠키를 찾는 메소드  //
    public <T> T getCookie (HttpServletRequest req, String name, Class<T> valueType) {
        Cookie cookie = getCookie(req, name);
        if(cookie == null) { return null; }
        if(valueType == String.class) {
            return (T) cookie.getValue();
        }
        return deserialize(cookie, valueType);
    }

    //  쿠키를 생성하고 현재 HTTP 응답에 추가하는 메소드  //
    public void setCookie(HttpServletResponse res, String name, String value, int maxAge) {

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");  // 해당 도메인의 모든 URL 에 대한 쿠키가 전송된다.
        cookie.setHttpOnly(true);  // 쿠키를 HTTP 전용으로 지정한다.
        cookie.setMaxAge(maxAge);  // 쿠키의 최대 유효시간을 지정한다.
        res.addCookie(cookie);  // 생성된 쿠키를 HTTP 응답에 추가한다.

    }
    public void setCookie(HttpServletResponse res, String name, Object obj, int maxAge) {
        this.setCookie(res, name, serialize(obj), maxAge);
    }

    //  쿠키를 삭제하는 메소드  //
    public void deleteCookie(HttpServletResponse res, String name) {

        setCookie(res, name, null, 0);

    }

    //  객체가 가지고 있는 데이터를 문자열로 변환(암호화)  //
    public String serialize(Object obj) {
        // Object > byte[] > String
        return Base64.getUrlEncoder().encodeToString( SerializationUtils.serialize(obj) );
    }

    //  객체가 가지고 있는 문자열을 데이터로 복호화  //
    public <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }

}
