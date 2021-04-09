package com.github.cloudoauth2.server.password;


import sun.misc.BASE64Encoder;

@SuppressWarnings({ "restriction" })
class MD5Codec {
	
	/**
	 * JDK 自带的 Base64 加密解码算法对象
	 */
	private static BASE64Encoder b = new BASE64Encoder();
	
	public static String encrypt(String plainText) {
		byte[] buffer = null;
		try {
			buffer = plainText.getBytes("UTF-8");
		} catch (Exception e) {
			buffer = plainText.getBytes();
		}
		return "{MD5}" + encrypt(buffer);
	}
	
	private static String encryptHex(String plainText) {
		byte[] result = null;
		try {
			result = DigestUtils.getDigest("MD5").digest(plainText.getBytes("UTF-8"));
			return getHexString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(result);
	}

	private static String getHexString(byte bt) {
		String hexStr = null;
		int n = bt;
		if (n < 0) {
			//移位算法
			n = bt & 0x7F + 128;
		}
		hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
		return hexStr.toUpperCase();
	}

	/**
	 * 字节标准移位转十六进制方法
	 */
	public static final String getHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes) {
			sb.append(getHexString(b));
		}
		return sb.toString();
	}
	
	public static String encrypt(byte[] plainBytes) {
		byte[] result = null;
		try {
			result = DigestUtils.getDigest("MD5").digest(plainBytes);
			return b.encode(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(result);
	}
	
	/**
	 * 密码验证方法
	 */
	public static boolean verify(String plantText, String encryptedText) {
		return encrypt(plantText).equals(encryptedText);
	}

	/**
	 * 提供一个测试的主函数
	 */
	public static void main(String[] args) {
		System.out.println(MD5Codec.encrypt("123456"));
		System.out.println(MD5Codec.encryptHex("123456"));
		/*select getmd5('123456') from dual

		{MD5}4QrcOUm6Wau+VuBX8g+IPg==
*/
		System.out.println("test:" + encrypt("ddddddddddddddddddddddddddddddddddddddddd").length());
		System.out.println("123:" + encrypt("123").length());
		System.out.println("123456789:" + encrypt("123456789").length());
		System.out.println("sarin:" + encrypt("sarin").length());
	}
}