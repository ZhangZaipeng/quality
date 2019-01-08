package com.hhh.quality.common;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * Created by ZhangZaipeng on 2017/8/11 0011.
 */
public class RSAUtils {

  // 定义加密方式
  private final static String KEY_RSA = "RSA";

  // 定义签名算法
  private final static String KEY_RSA_SIGNATURE = "MD5withRSA";

  // 定义公钥算法
  private final static String KEY_RSA_PUBLICKEY = "RSAPublicKey";

  // 定义私钥算法
  private final static String KEY_RSA_PRIVATEKEY = "RSAPrivateKey";

  // RSA最大加密明文大小
  private static final int MAX_ENCRYPT_BLOCK = 117;

  // RSA最大解密密文大小
  private static final int MAX_DECRYPT_BLOCK = 128;

  // 初始化密钥
  public static Map<String, Object> init() {
    Map<String, Object> map = null;
    try {
      KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
      generator.initialize(512);
      KeyPair keyPair = generator.generateKeyPair();
      // 公钥
      RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
      // 私钥
      RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
      // 将密钥封装为map
      map = new HashMap<String, Object>();
      map.put(KEY_RSA_PUBLICKEY, publicKey);
      map.put(KEY_RSA_PRIVATEKEY, privateKey);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return map;
  }

  /**
   * 用私钥对信息生成数字签名
   *
   * @param data 加密数据
   * @param privateKey 私钥
   */
  public static String sign(byte[] data, String privateKey) {
    String str = "";
    try {
      // 解密由base64编码的私钥
      byte[] bytes = decryptBase64(privateKey);
      // 构造PKCS8EncodedKeySpec对象
      PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);
      // 指定的加密算法
      KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
      // 取私钥对象
      PrivateKey key = factory.generatePrivate(pkcs);
      // 用私钥对信息生成数字签名
      Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
      signature.initSign(key);
      signature.update(data);
      str = encryptBase64(signature.sign());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return str;
  }

  /**
   * 校验数字签名
   *
   * @param data 加密数据
   * @param publicKey 公钥
   * @param sign 数字签名
   * @return 校验成功返回true，失败返回false
   */
  public static boolean verify(byte[] data, String publicKey, String sign) {
    boolean flag = false;
    try {
      // 解密由base64编码的公钥
      byte[] bytes = decryptBase64(publicKey);
      // 构造X509EncodedKeySpec对象
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
      // 指定的加密算法
      KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
      // 取公钥对象
      PublicKey key = factory.generatePublic(keySpec);
      // 用公钥验证数字签名
      Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
      signature.initVerify(key);
      signature.update(data);
      flag = signature.verify(decryptBase64(sign));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }

  /**
   * 私钥解密
   *
   * @param data 加密数据
   * @param key 私钥
   */
  public static byte[] decryptByPrivateKey(byte[] data, String key) {
    byte[] result = null;
    try {
      // 对私钥解密
      byte[] bytes = decryptBase64(key);
      // 取得私钥
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
      KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
      PrivateKey privateKey = factory.generatePrivate(keySpec);
      // 对数据解密
      Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
      cipher.init(Cipher.DECRYPT_MODE, privateKey);

      return cipher.doFinal(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 私钥解密
   *
   * @param data 加密数据
   * @param key 公钥
   */
  public static byte[] decryptByPublicKey(byte[] data, String key) {
    byte[] result = null;
    try {
      // 对公钥解密
      byte[] bytes = decryptBase64(key);
      // 取得公钥
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
      KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
      PublicKey publicKey = factory.generatePublic(keySpec);
      // 对数据解密
      Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
      cipher.init(Cipher.DECRYPT_MODE, publicKey);

      return cipher.doFinal(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 公钥加密
   *
   * @param data 待加密数据
   * @param key 公钥
   */
  public static byte[] encryptByPublicKey(byte[] data, String key) {
    byte[] result = null;
    try {
      byte[] bytes = decryptBase64(key);
      // 取得公钥
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
      KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
      PublicKey publicKey = factory.generatePublic(keySpec);
      // 对数据加密
      Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);

      result = cipher.doFinal(data);
      for (Byte a : result) {
        System.out.print(a + ",");
      }
      return result;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 私钥加密
   *
   * @param data 待加密数据
   * @param key 私钥
   */
  public static byte[] encryptByPrivateKey(byte[] data, String key) {
    byte[] result = null;
    try {
      byte[] bytes = decryptBase64(key);
      // 取得私钥
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
      KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
      PrivateKey privateKey = factory.generatePrivate(keySpec);
      // 对数据加密
      Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
      cipher.init(Cipher.ENCRYPT_MODE, privateKey);

      return cipher.doFinal(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 获取公钥
   */
  public static String getPublicKey(Map<String, Object> map) {
    String str = "";
    try {
      Key key = (Key) map.get(KEY_RSA_PUBLICKEY);
      str = encryptBase64(key.getEncoded());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return str;
  }

  /**
   * 获取私钥
   */
  public static String getPrivateKey(Map<String, Object> map) {
    String str = "";
    try {
      Key key = (Key) map.get(KEY_RSA_PRIVATEKEY);
      str = encryptBase64(key.getEncoded());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return str;
  }

  /**
   * BASE64 解密
   *
   * @param key 需要解密的字符串
   * @return 字节数组
   */
  public static byte[] decryptBase64(String key) throws Exception {
    return (new BASE64Decoder()).decodeBuffer(key);
  }

  /**
   * BASE64 加密
   *
   * @param key 需要加密的字节数组
   * @return 字符串
   */
  public static String encryptBase64(byte[] key) throws Exception {
    return (new BASE64Encoder()).encodeBuffer(key);
  }

  /**
   * 测试方法
   */
  public static void main(String[] args) {
    // 生成公钥私钥
    Map<String, Object> map = init();
    String publicKey = getPublicKey(map);
    String privateKey = getPrivateKey(map);
    // String publicKey = "";
    // String privateKey = "";

    System.out.println("公钥: \n\r" + publicKey);
    System.out.println("私钥： \n\r" + privateKey);

    System.out.println("公钥加密--------私钥解密");
    String word = "13308629859";
    byte[] encWord = encryptByPublicKey(word.getBytes(), publicKey);

    String decWord = new String(decryptByPrivateKey(encWord, privateKey));
    System.out.println("word 大小：" + word.length());
    System.out.println(
        "加密前: " + word + "\n\r" + "加密中: " + encWord.toString() + "\n\r" + "解密后: " + decWord);

    System.out.println("equals" + word.equals(decWord));

    System.out.println("私钥加密--------公钥解密");
    // String english = "Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!";
    String english = "Hello, World!Hello, World!Hello, World!Hello, World!Hello, World!Hel Hello, World!Hello, Wor";
    byte[] encEnglish = encryptByPrivateKey(english.getBytes(), privateKey);
    String decEnglish = new String(decryptByPublicKey(encEnglish, publicKey));
    System.out.println("word 大小：" + word.length());
    System.out.println(
        "加密前: " + english + "\n\r" + "加密中: " + encEnglish.toString() + "\n\r" + "解密后: "
            + decEnglish);
    System.out.println("equals" + english.equals(decEnglish));

    System.out.println("私钥签名——公钥验证签名");
    // 产生签名
    String sign = sign(encEnglish, privateKey);
    System.out.println("签名:\r" + sign);
    // 验证签名
    boolean status = verify(encEnglish, publicKey, sign);
    System.out.println("状态:\r" + status);
  }
}
