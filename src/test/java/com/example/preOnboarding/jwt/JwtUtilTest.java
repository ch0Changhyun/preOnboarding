package com.example.preOnboarding.jwt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.ExpiredJwtException;

class JwtUtilTest {

	private JwtUtil jwtUtil;

	@BeforeEach
	void setUp() {
		jwtUtil = new JwtUtil();
		jwtUtil.secretKey = "your-secret-key-your-secret-key-your-secret-key";
		jwtUtil.setEXPIRATION_TIME(1000L);
	}

	@Test
	void testGenerateAndValidateToken() {
		String token = jwtUtil.generateToken("testuser");

		assertNotNull(token);
		assertEquals("testuser", jwtUtil.extractUsername(token));
		assertTrue(jwtUtil.validateToken(token));
	}

	@Test
	void testExpiredToken() throws InterruptedException {
		jwtUtil.EXPIRATION_TIME = 1; // 1ms 후 만료되도록 설정
		String token = jwtUtil.generateToken("testuser");

		TimeUnit.MILLISECONDS.sleep(2);

		assertThrows(ExpiredJwtException.class, () -> jwtUtil.validateToken(token));
	}
}