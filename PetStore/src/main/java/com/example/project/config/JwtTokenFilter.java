package com.example.project.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.project.model.UserModel;
import com.example.project.util.JwtUtil;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;
	Logger loggerLocal = LogManager.getLogger(JwtTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!hasAuthorizationHeader(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		String accessToken = getAccessToken(request);
		if (!jwtUtil.validateAccessToken(accessToken)) {
			filterChain.doFilter(request, response);
			return;
		}
		setAuthenticationContext(accessToken, request);
		filterChain.doFilter(request, response);

	}

	private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
		UserDetails userDetails = getUserDetails(accessToken);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				null);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);

	}

	private UserDetails getUserDetails(String accessToken) {
		UserModel userDetails = new UserModel();
		String[] subjectArray = jwtUtil.getSubject(accessToken).split(",");
		userDetails.setId(Long.parseLong(subjectArray[0]));
		userDetails.setEmail(subjectArray[1]);
		return userDetails;
	}

	private boolean hasAuthorizationHeader(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		loggerLocal.info("Authorization : {}" , header);
		
		return !(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer"));
	}

	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		return header.split(" ")[1].trim();
	}

}
