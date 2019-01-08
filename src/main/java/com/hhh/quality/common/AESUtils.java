package com.hhh.quality.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtils {

  public static final String VIPARA = "0392039203920300";
  public static final String KEY = "hcwt888888888888";


  public static final String bm = "utf-8";

  private static final String KEY_ALGORITHM = "AES";
  private static final String DEFAULT_CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";//默认的加密算法
  private static final String DEFAULT_CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

  /**
   * AES 加密操作
   *
   * @param content 待加密内容
   * @param password 加密密码
   * @return 返回Base64转码后的加密数据
   */
  public static String encrypt(String content, String password) {
    try {
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_ECB);// 创建密码器

      byte[] byteContent = content.getBytes("utf-8");

      cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器

      byte[] result = cipher.doFinal(byteContent);// 加密

      return new String(new BASE64Encoder().encode(result));//通过Base64转码返回
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  /**
   * AES 解密操作
   *
   * @param content
   * @param password
   * @return
   */
  public static String decrypt(String content, String password) {

    try {
      //实例化
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_ECB);

      //使用密钥初始化，设置为解密模式
      cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

      //执行操作
      byte[] result = cipher.doFinal(new BASE64Decoder().decodeBuffer(content));

      return new String(result, "utf-8");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  /**
   * 生成加密秘钥
   *
   * @return
   */
  private static SecretKeySpec getSecretKey(final String password) {
    try {
      //返回生成指定算法密钥生成器的 KeyGenerator 对象
      KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
      SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
      secureRandom.setSeed(password.getBytes());

      //AES 要求密钥长度为 128
      kg.init(128, secureRandom);

      //生成一个密钥
      SecretKey secretKey = kg.generateKey();

      return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * AES 加密
   *
   * @param content 明文
   * @param password 生成秘钥的关键字
   */

  public static String AESEncode(String password, String content) {
    try {
      IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
      SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_CBC);
      cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
      byte[] encryptedData = cipher.doFinal(content.getBytes(bm));

      return new String(new BASE64Encoder().encode(encryptedData));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * AES 解密
   *
   * @param content 密文
   * @param password 生成秘钥的关键字
   */
  public static String AESDecode(String password, String content) {
    try {
      byte[] byteMi = new BASE64Decoder().decodeBuffer(content);
      // byte[] byteMi = Base64.decode(content);
//          byte[] byteMi=  str2ByteArray(content);
      IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
      SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_CBC);
      cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
      byte[] decryptedData = cipher.doFinal(byteMi);
      return new String(decryptedData, "utf-8");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) {
    String s = "130420877771000";

    System.out.println("s:" + s);

    String s1 = AESUtils.AESEncode( "hcwt888888888888", s);
    System.out.println("s1:" + s1);

    System.out.println("s2:"+AESUtils.AESDecode( "hcwt888888888888", s1));


  }

}
