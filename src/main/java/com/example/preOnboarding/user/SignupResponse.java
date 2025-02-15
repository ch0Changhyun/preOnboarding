package com.example.preOnboarding.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponse {
	private String username;
	private String nickname;
	private List<Authority> authorities;

	@Data
	@AllArgsConstructor
	public static class Authority {
		private String authorityName;
	}
}