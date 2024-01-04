package io.rgiaviti.tsae.services;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.rgiaviti.tsae.core.TOTP;

@Service
public class TSEService {

	private static final Integer DEFAULT_SKEY_LENGTH_BYTES = 20;
	private static final Integer DEFAULT_TOTP_CODE_LENGTH = 6;

	private TOTP totp;

	@Autowired
	public TSEService(final TOTP totp) {
		this.totp = totp;
	}

	public String getRandomSecretKey(final Integer keyLenghtBytes) {
		final byte[] bytes;
		if (keyLenghtBytes != null) {
			bytes = new byte[keyLenghtBytes];
		} else {
			bytes = new byte[DEFAULT_SKEY_LENGTH_BYTES];
		}

		final SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return new Base32().encodeToString(bytes);
	}

	public Boolean matchTotpCode(String secretKey, String code) {
		final String totpCode = getTotpCode(secretKey);
		return totpCode.equals(code);
	}

	public String getTotpCode(final String secretKey, final Integer totpCodeLength) {
		final String normalizedBase32Key = secretKey.replace(" ", "").toUpperCase();
		final byte[] bytes = new Base32().decode(normalizedBase32Key);
		final String hexKey = Hex.encodeHexString(bytes);
		final long time = (System.currentTimeMillis() / 1000) / 30;
		final String hexTime = Long.toHexString(time);
		return this.totp.generateTOTP(hexKey, hexTime, totpCodeLength);
	}

	public String getTotpCode(final String secretKey) {
		return getTotpCode(secretKey, DEFAULT_TOTP_CODE_LENGTH);
	}
}
