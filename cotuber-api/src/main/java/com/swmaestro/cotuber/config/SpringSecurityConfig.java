package com.swmaestro.cotuber.config;

import com.swmaestro.cotuber.TokenCreator;
import com.swmaestro.cotuber.auth.AuthService;
import com.swmaestro.cotuber.config.filter.JwtFilter;
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
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {
    private final UserReader userReader;
    private final AuthService authService;
    private final TokenCreator tokenCreator;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
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
                .build();
    }
}
