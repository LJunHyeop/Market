package com.example.market.user.config;

import com.example.market.jwt.JwtAuthenticationAccessDeniedHandler;
import com.example.market.jwt.JwtAuthenticationEntryPoint;
import com.example.market.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final OAuth2AuthenticationRequestBasedOnCookieRepository repository;
//    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
//    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
//    private final OAuth2AuthenticationCheckRedirectUriFilter oAuth2AuthenticationCheckRedirectUriFilter;
//    private final SocialLoginServiceImpl service;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless 세션 유지
                .httpBasic(http -> http.disable()) // HTTP Basic 인증 비활성화
                .formLogin(form -> form.disable()) // Form 로그인 비활성화
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/user/sign-up").permitAll() // 회원가입 API는 익명 사용자도 접근 가능하게 설정
                        .requestMatchers( "api/user**", "api/user/**" ).authenticated() // /api/user 하위 경로는 인증 필요
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // /api/admin/** 경로는 ADMIN 권한 필요
                        .anyRequest().permitAll() // 그 외 모든 요청은 허용
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // 인증 실패 처리
                        .accessDeniedHandler(new JwtAuthenticationAccessDeniedHandler()) // 접근 거부 처리
                )
                .build();
    }
//                .sessionManagement(
//                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .httpBasic(http -> http.disable())
//                .formLogin(form -> form.disable())
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(
//                        auth -> auth.requestMatchers( "api/user**", "api/user/**" ).authenticated()
//                                .requestMatchers("/api/user/sign-up").permitAll()
//                                .requestMatchers( "api/admin/**" ).hasRole("ADMIN")
//                                .anyRequest().permitAll())
//
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
//                        .accessDeniedHandler(new JwtAuthenticationAccessDeniedHandler()))
//                .oauth2Login( oauth2 -> oauth2.authorizationEndpoint(
//                                auth -> auth.baseUri("/oauth2/authorization")
//                                        .authorizationRequestRepository(repository))
//                        .redirectionEndpoint( redirection -> redirection.baseUri("/*/oauth2/code/*"))
//                        .userInfoEndpoint(userInfo -> userInfo.userService(service))
//                        .successHandler(oAuth2AuthenticationSuccessHandler)
//                        .failureHandler(oAuth2AuthenticationFailureHandler)
//                )
//                .addFilterBefore(oAuth2AuthenticationCheckRedirectUriFilter, OAuth2AuthorizationRequestRedirectFilter.class)
//                .build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}