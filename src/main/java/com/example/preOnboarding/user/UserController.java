package com.example.preOnboarding.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
		SignupResponse response = userService.signup(request.getUsername(), request.getPassword(), request.getNickname());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/sign")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		String token = userService.login(request.getUsername(), request.getPassword());
		LoginResponse response = new LoginResponse(token);
		return ResponseEntity.ok(response);
	}
}
