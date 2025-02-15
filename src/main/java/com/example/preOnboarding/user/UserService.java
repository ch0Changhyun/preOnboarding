package com.example.preOnboarding.user;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.preOnboarding.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public SignupResponse signup(String username, String password, String nickname) {
		User user = new User(username, passwordEncoder.encode(password), nickname);
		userRepository.save(user);
		return new SignupResponse(username, nickname, Collections.singletonList(new SignupResponse.Authority("ROLE_USER")));
	}

	public String login(String username, String password) {
		User user = userRepository.findByUsername(username).orElseThrow();
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		return jwtUtil.generateToken(username);
	}
}