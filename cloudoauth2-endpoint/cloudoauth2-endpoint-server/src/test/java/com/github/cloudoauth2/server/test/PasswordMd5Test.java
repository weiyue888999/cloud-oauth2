package com.github.cloudoauth2.server.test;
import com.github.cloudoauth2.server.password.Md5PasswordEncoder;
import org.junit.Test;

public class PasswordMd5Test {

	@Test
	public void test() {
		Md5PasswordEncoder pe = new Md5PasswordEncoder();
		String en = pe.encode("123456");
		System.out.println(en);
	}
}
