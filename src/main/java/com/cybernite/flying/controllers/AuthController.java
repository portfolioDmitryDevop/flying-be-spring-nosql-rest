package com.cybernite.flying.controllers;

import com.cybernite.flying.common.Constants;
import com.cybernite.flying.dto.LoginData;
import com.cybernite.flying.entities.Account;
import com.cybernite.flying.exeptions.FlyingBudRequestExeption;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.security.JWTUtil;
import com.cybernite.flying.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(Constants.AUTH.LOGIN_MAPPING)
@Log4j2
@AllArgsConstructor
@Validated
public class AuthController {

	private AccountService accountService;
	private JWTUtil jwtUtil;
	private PasswordEncoder passwordEncoder;

	@PostMapping(Constants.AUTH.LOGIN_SIGNUP)
	public Map<String, Object> registerHandler(@Valid @RequestBody LoginData request) throws FlyingBudRequestExeption {
		String encodedPass = passwordEncoder.encode(request.getPassword());
		request.setPassword(encodedPass);
		request = accountService.createAccount(request);
		String token = jwtUtil.create(request.getEmail());
		return Collections.singletonMap("jwt-token", token);
	}

	@PostMapping(Constants.AUTH.LOGIN_SIGNIN)
	public ResponseEntity<?> loginHandler(@Valid @RequestBody LoginData request) throws FlyingNotFoundExeption {
		Account account = accountService.getAccountByEmail(request.getEmail());
		if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
			throw new FlyingNotFoundExeption("Wrong name or password");
		}
		return new ResponseEntity<>(jwtUtil.getToken(request, account), HttpStatus.CREATED);
	}

}
