package com.github.cloudoauth2.endpoint.client.test;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

public class PublicKeyTest {

	@Test
	public void test() {
		ClassPathResource resource = new ClassPathResource("weiguangyue-public.cer");
		String publicKey = "";
		try {
			publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
			System.out.println(publicKey);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
