package com.example.preOnboarding.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@RequiredArgsConstructor
@Setter
public class JwtUtil {

	@Value("${service.jwt.secret-key}")
	public String secretKey;

	@Value("${service.jwt.access-expiration}")
	public long EXPIRATION_TIME;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String username) {
		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	public boolean validateToken(String token) {
		return getClaims(token).getExpiration().after(new Date());
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}