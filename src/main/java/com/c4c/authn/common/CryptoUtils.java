package com.c4c.authn.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The type Crypto utils.
 */
public class CryptoUtils {

  private CryptoUtils() {

  }

  /**
   * Get random nonce byte [ ].
   *
   * @param numBytes the num bytes
   * @return the byte [ ]
   */
  public static byte[] getRandomNonce(int numBytes) {
    byte[] nonce = new byte[numBytes];
    new SecureRandom().nextBytes(nonce);
    return nonce;
  }

  /**
   * Gets aes key.
   *
   * @param keySize the keySize
   * @return the aes key
   * @throws NoSuchAlgorithmException the no such algorithm exception
   */
  public static SecretKey getAESKey(int keySize) throws NoSuchAlgorithmException {
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(keySize, SecureRandom.getInstanceStrong());
    return keyGen.generateKey();
  }


  /**
   * Gets aes key from password.
   *
   * @param password the password
   * @param salt     the salt
   * @return the aes key from password
   * @throws NoSuchAlgorithmException the no such algorithm exception
   * @throws InvalidKeySpecException  the invalid key spec exception
   */
  public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {

    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    // iterationCount = 65536
    // keyLength = 256
    KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
    return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

  }


  /**
   * Hex string.
   *
   * @param bytes the bytes
   * @return the string
   */
  public static String hex(byte[] bytes) {
    StringBuilder result = new StringBuilder();
    for (byte b : bytes) {
      result.append(String.format("%02x", b));
    }
    return result.toString();
  }

  /**
   * Hex with block size string.
   *
   * @param bytes     the bytes
   * @param blockSize the block size
   * @return the string
   */
  public static String hexWithBlockSize(byte[] bytes, int blockSize) {

    String hex = hex(bytes);

    // one hex = 2 chars
    blockSize = blockSize * 2;

    // better idea how to print this?
    List<String> result = new ArrayList<>();
    int index = 0;
    while (index < hex.length()) {
      result.add(hex.substring(index, Math.min(index + blockSize, hex.length())));
      index += blockSize;
    }

    return result.toString();

  }

}