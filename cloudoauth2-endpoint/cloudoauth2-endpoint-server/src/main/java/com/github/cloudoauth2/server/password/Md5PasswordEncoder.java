package com.github.cloudoauth2.server.password;

import org.springframework.security.crypto.password.PasswordEncoder;

public class Md5PasswordEncoder implements PasswordEncoder{

	@Override
	public String encode(CharSequence rawPassword) {
		return MD5Codec.encrypt(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String encoded = this.encode(rawPassword);
		return encoded.equals(encodedPassword);
	}
}
