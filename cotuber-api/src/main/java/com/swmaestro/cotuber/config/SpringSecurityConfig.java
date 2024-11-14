package com.swmaestro.cotuber.config;

import com.swmaestro.cotuber.TokenCreator;
import com.swmaestro.cotuber.auth.AuthService;
import com.swmaestro.cotuber.config.constants.TokenConstants;
import com.swmaestro.cotuber.config.filter.JwtFilter;
import com.swmaestro.cotuber.config.filter.ReissueTokenFilter;
import com.swmaestro.cotuber.config.oauth.CustomAuthorizationRequestResolver;
import com.swmaestro.cotuber.config.oauth.CustomOAuth2UserService;
import com.swmaestro.cotuber.config.oauth.OAuth2FailureHandler;
import com.swmaestro.cotuber.config.oauth.OAuth2SuccessHandler;
import com.swmaestro.cotuber.user.UserReader;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {
    private final UserReader userReader;
    private final AuthService authService;
    private final TokenCreator tokenCreator;
    private final TokenConstants tokenConstants;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MockPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {

                })
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/**").permitAll();
                })
                .exceptionHandling(it -> {
                    it.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    });

                    it.authenticationEntryPoint((request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    });
                })
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(it -> it.authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository)))
                        .userInfoEndpoint(c -> c.userService(customOAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                )
                .addFilterBefore(new JwtFilter(userReader, authService, tokenCreator), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ReissueTokenFilter(authService, tokenConstants), JwtFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:8080",
                "https://localhost:3000",
                "https://localhost:8080",
                "https://cotuber.com",
                "https://develop.cotuber.com",
                "https://api.cotuber.com"
        ));
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "PATCH", "OPTION")); //요청 메소드
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type")); //허용할 헤더
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
