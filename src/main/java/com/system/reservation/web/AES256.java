package com.system.reservation.web;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class AES256 {
  private String iv;
  private Key keySpec;


  public AES256(String key) throws UnsupportedEncodingException {
    this.iv = key.substring(0, 16);
    byte[] keyBytes = new byte[16];
    byte[] b = key.getBytes("UTF-8");
    int len = b.length;
    if(len > keyBytes.length){
      len = keyBytes.length;
    }
    System.arraycopy(b, 0, keyBytes, 0, len);
    SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

    this.keySpec = keySpec;
  }


  public String encrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException{
    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
    byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
    String enStr = new String(Base64.encodeBase64(encrypted));
    return enStr;
  }


  public String decrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException {
    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
    byte[] byteStr = Base64.decodeBase64(str.getBytes());
    return new String(c.doFinal(byteStr), "UTF-8");
  }

}
