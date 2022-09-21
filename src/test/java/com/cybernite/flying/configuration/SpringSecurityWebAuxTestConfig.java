package com.cybernite.flying.configuration;

import com.cybernite.flying.entities.Account;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        Account account = new Account("dmitry@mail.ru", "password", "ROLE_ADMIN");
        User user = new User(
                account.getEmail(),
                account.getPassword(),
                AuthorityUtils.createAuthorityList(account.getRole()));


        return new InMemoryUserDetailsManager(Arrays.asList(
                user
        ));
    }
}
