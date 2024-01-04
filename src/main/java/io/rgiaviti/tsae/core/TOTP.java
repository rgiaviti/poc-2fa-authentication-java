package io.rgiaviti.tsae.core;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class TOTP {

	private static final int[] DIGITS_POWER = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };

	public String generateTOTP(String key, String time, Integer returnDigits) {
		return generateTOTP(key, time, returnDigits, "HmacSHA1");
	}

	public String generateTOTP256(String key, String time, Integer returnDigits) {
		return generateTOTP(key, time, returnDigits, "HmacSHA256");
	}

	public String generateTOTP512(String key, String time, Integer returnDigits) {
		return generateTOTP(key, time, returnDigits, "HmacSHA512");
	}

	public String generateTOTP(String key, String time, Integer returnDigits, String crypto) {
		String result = null;

		while (time.length() < 16) {
			time = "0" + time;
		}

		byte[] msg = hexStr2Bytes(time);
		byte[] k = hexStr2Bytes(key);
		byte[] hash = hmacSha(crypto, k, msg);

		int offset = hash[hash.length - 1] & 0xf;

		int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16)
				| ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

		int otp = binary % DIGITS_POWER[returnDigits];

		result = Integer.toString(otp);
		while (result.length() < returnDigits) {
			result = "0" + result;
		}

		return result;
	}

	private byte[] hmacSha(String crypto, byte[] keyBytes, byte[] text) {
		try {
			Mac hmac = Mac.getInstance(crypto);
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
			hmac.init(macKey);
			return hmac.doFinal(text);
		} catch (GeneralSecurityException gse) {
			throw new UndeclaredThrowableException(gse);
		}
	}

	private byte[] hexStr2Bytes(String hex) {
		byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
		byte[] ret = new byte[bArray.length - 1];

		for (int i = 0; i < ret.length; i++) {
			ret[i] = bArray[i + 1];
		}

		return ret;
	}
}