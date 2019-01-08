package com.hhh.quality.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ApiSignatureUtils {

  /**
   * 得出  Signature
   * @param appAccessKey AppKeyId
   * @param appSecretKey AppKeySecret
   * eg：http://www.ex.com/api/getAccount?id=123123
   * @param uri api/getAccount
   * @param method 请求方法，"GET"或"POST"
   * params 请求参数
   */
  public static String getSignature(String appAccessKey, String appSecretKey,
      String uri,String method, Map<String, String> params, String nbpwd) {

    StringBuilder sb = new StringBuilder(1024);
    sb.append(method.toUpperCase()).append('\n') // GET
        .append(uri).append('\n'); // /path
    params.remove("Signature");
    params.put("AccessKey", appAccessKey);

    if(nbpwd != null){
      params.put("nbPwd", nbpwd);
    }

    // build signature:
    SortedMap<String, String> map = new TreeMap<>(params);
    for (Map.Entry<String, String> entry : map.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if(!StringUtils.isEmpty(value)){
        sb.append(key).append('=').append(urlEncode(value)).append('&');
      }
    }
    // remove last '&':
    sb.deleteCharAt(sb.length() - 1);
    // sign:
    Mac hmacSha256 = null;
    try {
      hmacSha256 = Mac.getInstance("HmacSHA256");
      SecretKeySpec secKey =
          new SecretKeySpec(appSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
      hmacSha256.init(secKey);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("No such algorithm: " + e.getMessage());
    } catch (InvalidKeyException e) {
      throw new RuntimeException("Invalid key: " + e.getMessage());
    }
    String payload = sb.toString();
    byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));
    String actualSign = Base64.getEncoder().encodeToString(hash);

    return actualSign;
  }

  /**
   * 时间生成工具
   * 10位 时间戳
   */
  public static String getTimestap() {
    Long timestap = System.currentTimeMillis()/1000;
    return timestap.toString();
  }

  public static String urlEncode(String s) {
    try {
      return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException("UTF-8 encoding not supported!");
    }
  }

}
