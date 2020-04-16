package com.musabrt;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class TDes {
	private byte[] key = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01,
			0x01, 0x01, 0x02, 0x02, 0x02, 0x02, 0x02, 0x02, 0x02, 0x02 };

	private byte[] keyiv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	public String encode(String args) {

		//System.out.println("plain data==>  " + args);

		byte[] encoding;
		try {
			encoding = Base64.getEncoder().encode(args.getBytes("UTF-8"));

			//System.out.println("Base64.encodeBase64==>" + new String(encoding));
			byte[] str5 = des3EncodeCBC(key, keyiv, encoding);

			//System.out.println("des3EncodeCBC==>  " + new String(str5));

			assert str5 != null;
			byte[] encoding1 = Base64.getEncoder().encode(str5);
			//System.out.println("Base64.encodeBase64==> " + new String(encoding1));
			return new String(encoding1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decode(String args) {
		try {
			//System.out.println("encrypted data==>" + new String(args.getBytes("UTF-8")));

			byte[] decode = Base64.getDecoder().decode(args.getBytes("UTF-8"));
			//System.out.println("Base64.decodeBase64(main encription)==>" + new String(decode));

			byte[] str6 = des3DecodeCBC(key, keyiv, decode);
			//System.out.println("des3DecodeCBC==>" + new String(str6));
			assert str6 != null;
			String data = new String(str6);
			byte[] decode1 = Base64.getDecoder().decode(data.trim().getBytes("UTF-8"));
			//System.out.println("plaintext==>  " + new String(decode1));
			return new String(decode1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "u mistaken in try block";

	}

	private byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) {
		try {
			Key deskey;
			DESedeKeySpec spec = new DESedeKeySpec(key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance("desede/ CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(keyiv);
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			return cipher.doFinal(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) {
		try {
			Key deskey;
			DESedeKeySpec spec = new DESedeKeySpec(key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance("desede/ CBC/NoPadding");// PKCS5Padding NoPadding
			IvParameterSpec ips = new IvParameterSpec(keyiv);
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

			return cipher.doFinal(data);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}