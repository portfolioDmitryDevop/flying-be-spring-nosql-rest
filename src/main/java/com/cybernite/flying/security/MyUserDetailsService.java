package com.cybernite.flying.security;

import com.cybernite.flying.dto.LoginData;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Log4j2
public class MyUserDetailsService implements UserDetailsService {

    AccountService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            LoginData loginData = service.readAccountByEmail(email);
            User user = new User(
                    email,
                    loginData.getPassword(),
                    AuthorityUtils.createAuthorityList(service.getRoleAccount(email)));
            return user;

        } catch (FlyingNotFoundExeption e) {
            throw new UsernameNotFoundException(String.format("Could not findUser with email %s", email));
        }
    }
}
