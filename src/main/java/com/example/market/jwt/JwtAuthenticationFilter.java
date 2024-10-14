package com.example.market.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request);

        log.info("JwtAuthenticationFilter-Token: {}", token);
        log.info("Is token valid: {}", jwtTokenProvider.isValidateToken(token));


        if(token != null && jwtTokenProvider.isValidateToken(token)) {

            Authentication auth = jwtTokenProvider.getAuthentication(token);
            if(auth != null) { SecurityContextHolder.getContext().setAuthentication(auth); }

        }

        filterChain.doFilter(request, response);
    }
}
