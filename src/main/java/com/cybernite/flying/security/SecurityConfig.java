package com.cybernite.flying.security;

import com.cybernite.flying.common.Constants;
import com.cybernite.flying.exeptions.SecurityExceptionsHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@AllArgsConstructor
@Log4j2
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JWTFilter filter;
    private SecurityExceptionsHandler securityExceptions;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests()
                .antMatchers(Constants.AUTH.LOGIN_MAPPING + "/**").permitAll()
                .antMatchers(Constants.WebSocket.ENDPOINT + "/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.GET).hasAnyRole(Constants.ROLE.ADMIN, Constants.ROLE.USER)
                .anyRequest().hasRole(Constants.ROLE.ADMIN);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(securityExceptions)
                .authenticationEntryPoint(securityExceptions);
    }


}
