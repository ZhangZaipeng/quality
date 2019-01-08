package com.hhh.quality.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPwdLevelUtil {

  private static String HASNUM = "\\d";

  private static String HASLOWER = "[a-z]";

  private static String HASUPPER = "[A-Z]";

  private static String HASLETTER = "\\W";

  public static short checkPasswordStrength(String password) {
    int score = 0;
    int level = 1;
    if(password.length() >= 8 && password.length() <= 20){
      if(pattern(password,HASNUM)){
        score++;
      }
      if(pattern(password,HASLOWER)){
        score++;
      }
      if(pattern(password,HASUPPER)){
        score++;
      }
      if(pattern(password,HASLETTER)){
        score++;
      }
      if(score > 1 && password.length() > 12){
        score++;
      }
    }
    if(score == 3){
      level = 2;
    }
    if(score >= 4){
      level = 3;
    }
    return (short) level;
  }

  public static boolean pattern(String content,String pattern) {
    boolean flag = false;
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(content);
    if (m.matches()){
      flag = true;
    }
    return flag;
  }
}
