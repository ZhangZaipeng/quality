package com.hhh.quality.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

public class Base64 {

  public static String byteArrayToBase64(byte[] a) {
    return byteArrayToBase64(a, false);
  }

  public static String byteArrayToAltBase64(byte[] a) {
    return byteArrayToBase64(a, true);
  }

  private static String byteArrayToBase64(byte[] a, boolean alternate) {
    int aLen = a.length;
    int numFullGroups = aLen / 3;
    int numBytesInPartialGroup = aLen - 3 * numFullGroups;
    int resultLen = 4 * ((aLen + 2) / 3);
    StringBuffer result = new StringBuffer(resultLen);
    char[] intToAlpha = alternate ? intToAltBase64 : intToBase64;

    int inCursor = 0;
    for (int i = 0; i < numFullGroups; i++) {
      int byte0 = a[(inCursor++)] & 0xFF;
      int byte1 = a[(inCursor++)] & 0xFF;
      int byte2 = a[(inCursor++)] & 0xFF;
      result.append(intToAlpha[(byte0 >> 2)]);
      result.append(intToAlpha[(byte0 << 4 & 0x3F | byte1 >> 4)]);
      result.append(intToAlpha[(byte1 << 2 & 0x3F | byte2 >> 6)]);
      result.append(intToAlpha[(byte2 & 0x3F)]);
    }

    if (numBytesInPartialGroup != 0) {
      int byte0 = a[(inCursor++)] & 0xFF;
      result.append(intToAlpha[(byte0 >> 2)]);
      if (numBytesInPartialGroup == 1) {
        result.append(intToAlpha[(byte0 << 4 & 0x3F)]);
        result.append("==");
      } else {
        int byte1 = a[(inCursor++)] & 0xFF;
        result.append(intToAlpha[(byte0 << 4 & 0x3F | byte1 >> 4)]);
        result.append(intToAlpha[(byte1 << 2 & 0x3F)]);
        result.append('=');
      }
    }

    return result.toString();
  }

  private static final char[] intToBase64 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
      'L', 'M', 'N',
      'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
      'h', 'i',
      'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
      '2', '3',
      '4', '5', '6', '7', '8', '9', '+', '/'};

  private static final char[] intToAltBase64 = {'!', '"', '#', '$', '%', '&', '\'', '(', ')', ',',
      '-', '.', ':',
      ';', '<', '>', '@', '[', ']', '^', '`', '_', '{', '|', '}', '~', 'a', 'b', 'c', 'd', 'e', 'f',
      'g', 'h',
      'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0',
      '1', '2',
      '3', '4', '5', '6', '7', '8', '9', '+', '?'};

  public static byte[] base64ToByteArray(String s) {
    return base64ToByteArray(s, false);
  }

  public static byte[] altBase64ToByteArray(String s) {
    return base64ToByteArray(s, true);
  }

  private static byte[] base64ToByteArray(String s, boolean alternate) {
    byte[] alphaToInt = alternate ? altBase64ToInt : base64ToInt;
    int sLen = s.length();
    int numGroups = sLen / 4;
    if (4 * numGroups != sLen) {
      throw new IllegalArgumentException("String length must be a multiple of four.");
    }
    int missingBytesInLastGroup = 0;
    int numFullGroups = numGroups;
    if (sLen != 0) {
      if (s.charAt(sLen - 1) == '=') {
        missingBytesInLastGroup++;
        numFullGroups--;
      }
      if (s.charAt(sLen - 2) == '=') {
        missingBytesInLastGroup++;
      }
    }
    byte[] result = new byte[3 * numGroups - missingBytesInLastGroup];
    char[] a = s.toCharArray();

    int inCursor = 0;
    int outCursor = 0;
    for (int i = 0; i < numFullGroups; i++) {
      int ch0 = base64toInt(a[(inCursor++)], alphaToInt);
      int ch1 = base64toInt(a[(inCursor++)], alphaToInt);
      int ch2 = base64toInt(a[(inCursor++)], alphaToInt);
      int ch3 = base64toInt(a[(inCursor++)], alphaToInt);
      result[(outCursor++)] = ((byte) (ch0 << 2 | ch1 >> 4));
      result[(outCursor++)] = ((byte) (ch1 << 4 | ch2 >> 2));
      result[(outCursor++)] = ((byte) (ch2 << 6 | ch3));
    }

    if (missingBytesInLastGroup != 0) {
      int ch0 = base64toInt(a[(inCursor++)], alphaToInt);
      int ch1 = base64toInt(a[(inCursor++)], alphaToInt);
      result[(outCursor++)] = ((byte) (ch0 << 2 | ch1 >> 4));

      if (missingBytesInLastGroup == 1) {
        int ch2 = base64toInt(a[(inCursor++)], alphaToInt);
        result[(outCursor++)] = ((byte) (ch1 << 4 | ch2 >> 2));
      }
    }

    return result;
  }

  private static int base64toInt(char c, byte[] alphaToInt) {
    int result = alphaToInt[c];
    if (result < 0) {
      throw new IllegalArgumentException("Illegal character " + c);
    }
    return result;
  }

  private static final byte[] base64ToInt = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      -1, -1, -1, -1,
      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      -1, -1, 62, -1,
      -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
      5, 6, 7, 8,
      9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26,
      27, 28, 29,
      30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};

  private static final byte[] altBase64ToInt = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      -1, -1, -1, -1,
      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, -1,
      62, 9, 10,
      11, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 12, 13, 14, -1, 15, 63, 16, -1, -1, -1, -1,
      -1, -1, -1, -1,
      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, -1, 18, 19, 21,
      20, 26, 27, 28,
      29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,
      22, 23, 24,
      25};

  public static void main(String[] args) {
    int numRuns = 10;
    int numBytes = 20;
    Random rnd = new Random();
    for (int i = 0; i < numRuns; i++) {
      for (int j = 0; j < numBytes; j++) {
        byte[] arr = new byte[j];
        for (int k = 0; k < j; k++) {
          arr[k] = ((byte) rnd.nextInt());
        }
        String s = byteArrayToBase64(arr);
        byte[] b = base64ToByteArray(s);
        if (!Arrays.equals(arr, b)) {
          System.out.println("Dismal failure!");
        }
        s = byteArrayToAltBase64(arr);
        b = altBase64ToByteArray(s);
        if (!Arrays.equals(arr, b)) {
          System.out.println("Alternate dismal failure!");
        }
      }
    }
  }


  private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
      .toCharArray();

  public static String encode(byte[] data) {
    int start = 0;
    int len = data.length;
    StringBuffer buf = new StringBuffer(data.length * 3 / 2);

    int end = len - 3;
    int i = start;
    int n = 0;

    while (i <= end) {
      int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8) | (
          ((int) data[i + 2]) & 0x0ff);

      buf.append(legalChars[(d >> 18) & 63]);
      buf.append(legalChars[(d >> 12) & 63]);
      buf.append(legalChars[(d >> 6) & 63]);
      buf.append(legalChars[d & 63]);

      i += 3;

      if (n++ >= 14) {
        n = 0;
        buf.append(" ");
      }
    }

    if (i == start + len - 2) {
      int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

      buf.append(legalChars[(d >> 18) & 63]);
      buf.append(legalChars[(d >> 12) & 63]);
      buf.append(legalChars[(d >> 6) & 63]);
      buf.append("=");
    } else if (i == start + len - 1) {
      int d = (((int) data[i]) & 0x0ff) << 16;

      buf.append(legalChars[(d >> 18) & 63]);
      buf.append(legalChars[(d >> 12) & 63]);
      buf.append("==");
    }

    return buf.toString();
  }

  private static int decode(char c) {
    if (c >= 'A' && c <= 'Z') {
      return ((int) c) - 65;
    } else if (c >= 'a' && c <= 'z') {
      return ((int) c) - 97 + 26;
    } else if (c >= '0' && c <= '9') {
      return ((int) c) - 48 + 26 + 26;
    } else {
      switch (c) {
        case '+':
          return 62;
        case '/':
          return 63;
        case '=':
          return 0;
        default:
          throw new RuntimeException("unExpected code: " + c);
      }
    }
  }

  /**
   * Decodes the given Base64 encoded String to a new byte array. The byte array holding the decoded
   * data is returned.
   */

  public static byte[] decode(String s) {

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      decode(s, bos);
    } catch (IOException e) {
      throw new RuntimeException();
    }
    byte[] decodedBytes = bos.toByteArray();
    try {
      bos.close();
      bos = null;
    } catch (IOException ex) {
      System.err.println("Error while decoding BASE64: " + ex.toString());
    }
    return decodedBytes;
  }

  private static void decode(String s, OutputStream os) throws IOException {
    int i = 0;

    int len = s.length();

    while (true) {
      while (i < len && s.charAt(i) <= ' ') {
        i++;
      }

      if (i == len) {
        break;
      }

      int tri =
          (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2))
              << 6) + (decode(s.charAt(i + 3)));

      os.write((tri >> 16) & 255);
      if (s.charAt(i + 2) == '=') {
        break;
      }
      os.write((tri >> 8) & 255);
      if (s.charAt(i + 3) == '=') {
        break;
      }
      os.write(tri & 255);

      i += 4;
    }
  }

}
