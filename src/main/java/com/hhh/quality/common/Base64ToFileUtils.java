package com.hhh.quality.common;

import java.io.IOException;
import sun.misc.BASE64Decoder;

public class Base64ToFileUtils {
  /**
   * base64字符串转文件
   * @param base64
   * @return
   */
  public static BASE64DecodedMultipartFile base64ToMultipart(String base64) {
    try {
      String[] baseStrs = base64.split(",");

      BASE64Decoder decoder = new BASE64Decoder();
      byte[] b = new byte[0];
      b = decoder.decodeBuffer(baseStrs[0]);

      for(int i = 0; i < b.length; ++i) {
        if (b[i] < 0) {
          b[i] += 256;
        }
      }

      return new BASE64DecodedMultipartFile(b, baseStrs[0]);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
