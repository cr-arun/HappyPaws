package com.example.project.util;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.project.model.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	Logger logger = LogManager.getLogger(JwtUtil.class);
	private static final long EXPIRE_DURATION = (long)60 * 60 * 1000;
	@Value("${app.jwt.secret}")
	private String secretKey;

	public String generateAccessToken(UserModel user) {
		return Jwts.builder().setSubject(String.format("%s,%s", user.getId(), user.getEmail())).setIssuer("PetStore")
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			logger.info("JWT Expired {}" , ex.getMessage());
		} catch (IllegalArgumentException ex) {
			logger.info("Token Null {}" , ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			logger.info("Not Supporetd {}" , ex.getMessage());
		} catch (SignatureException ex) {
			logger.info("Signature fail {}" , ex.getMessage());
		}
		return false;
	}

	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}

	private Claims parseClaims(String token) {

		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
}
