package com.kcs.stepstory.security.config;

import lombok.RequiredArgsConstructor;
import com.kcs.stepstory.constants.Constants;
import com.kcs.stepstory.security.filter.GlobalLoggerFilter;
import com.kcs.stepstory.security.filter.JwtAuthenticationFilter;
import com.kcs.stepstory.security.filter.JwtExceptionFilter;
import com.kcs.stepstory.security.handler.jwt.JwtAccessDeniedHandler;
import com.kcs.stepstory.security.handler.jwt.JwtAuthEntryPoint;
import com.kcs.stepstory.security.handler.signin.DefaultFailureHandler;
import com.kcs.stepstory.security.handler.signin.DefaultSuccessHandler;
import com.kcs.stepstory.security.handler.singout.CustomSignOutProcessHandler;
import com.kcs.stepstory.security.handler.singout.CustomSignOutResultHandler;
import com.kcs.stepstory.security.service.CustomUserDetailService;
import com.kcs.stepstory.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DefaultSuccessHandler defaultSuccessHandler;
    private final DefaultFailureHandler defaultFailureHandler;
    private final CustomSignOutProcessHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                                .requestMatchers(Constants.ADMIN_URLS.toArray(String[]::new)).hasRole("ADMIN")
                                .requestMatchers(Constants.USER_URLS.toArray(String[]::new)).hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated()
                )

                .formLogin(configurer ->
                        configurer
                                .loginPage("/login")
                                .loginProcessingUrl("/auth/sign-in")
                                .usernameParameter("serial_id")
                                .passwordParameter("password")
                                .successHandler(defaultSuccessHandler)
                                .failureHandler(defaultFailureHandler)
                )

                .logout(configurer ->
                        configurer
                                .logoutUrl("/auth/sign-out")
                                .addLogoutHandler(customSignOutProcessHandler)
                                .logoutSuccessHandler(customSignOutResultHandler)
                )

                .exceptionHandling(configurer ->
                        configurer
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, customUserDetailService),
                        LogoutFilter.class)
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class)
                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        JwtExceptionFilter.class)

                .getOrBuild();
    }
}
