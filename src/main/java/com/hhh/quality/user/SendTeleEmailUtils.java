package com.hhh.quality.user;

import com.estate.common.HBSmsUtil;
import com.estate.common.enums.VerifyCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendTeleEmailUtils {

  private final static Logger log = LoggerFactory.getLogger(SendTeleEmailUtils.class);

  private static String SYSNAMEEN;
  @Value("${hb.label}")
  public void setSysNameEn(String sysNameEn) {
    SYSNAMEEN = sysNameEn;
  }

  public static boolean sendTelephone(String[] responseStr, String verifyCode, String telephone,int codeType){
    String codeName = VerifyCodeEnum.getCodeName(codeType);
    // 发送短信 一共尝试3次
    boolean sendMsgFlag = false;
    for (int i = 0; i < 3; i++) {
      sendMsgFlag = HBSmsUtil
          .sendMessage(telephone, "【" +  SYSNAMEEN + "】您正在进行" + codeName +"，验证码是：" +
          verifyCode);
      if (sendMsgFlag) {
        responseStr[0] = "验证码已发送到您的手机" + telephone.substring(0,3) + "***" + telephone.substring(7) + "，5分钟内有效。";
        log.info("发送手机短信验证码成功 account ===> {} code {}", telephone, verifyCode);
        break;
      }
    }
    return sendMsgFlag;
  }
}
