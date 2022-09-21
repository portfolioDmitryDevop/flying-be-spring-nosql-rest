package com.cybernite.flying.exeptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Component
public class SecurityExceptionsHandler implements AccessDeniedHandler, AuthenticationEntryPoint {
	Logger LOG = LoggerFactory.getLogger(SecurityExceptionsHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Principal principal = request.getUserPrincipal();
		LOG.error("Authorization error for user {} from address {}", principal.getName(), request.getRemoteAddr());
		response.getWriter().printf("User %s disallowed to run request %s ", principal.getName(),
				request.getRequestURL());
		response.setStatus(403);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		LOG.error("Authentication error request {}", request.getRequestURL());
		response.getWriter().printf("Authentication error from request %s", request.getRequestURL());
		response.setStatus(401);

	}

}
