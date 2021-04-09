package com.github.cloudoauth2.server.password;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


class DigestUtils {
	
	private static final int STREAM_BUFFER_LENGTH = 1024;

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static byte[] md5(String source){
		return md5(source.getBytes());
	}
	
	public static byte[] md5(byte[] source){
		return DigestUtils.getDigest("MD5").digest(source);
	}
	
	public static byte[] md5(InputStream source) throws IOException{
		return digest(DigestUtils.getDigest("MD5"),source);
	}
	
	public static byte[] digest(MessageDigest digest, InputStream data) throws IOException {
        byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }
        return digest.digest();
    }
	
	public static MessageDigest getDigest(String algorithm) {
        try {
        	return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
	
	public static MessageDigest getDigest(String algorithm,String provider) {
        try {
        	return MessageDigest.getInstance(algorithm, provider);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
