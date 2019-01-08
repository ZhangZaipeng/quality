package com.hhh.quality.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

public class VerifyCodeUtils {

  //使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
  public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
  private static Random random = new Random();


  /**
   * 使用系统默认字符源生成验证码
   *
   * @param verifySize 验证码长度
   */
  public static String generateVerifyCode(int verifySize) {
    return generateVerifyCode(verifySize, VERIFY_CODES);
  }

  /**
   * 使用指定源生成验证码
   *
   * @param verifySize 验证码长度
   * @param sources 验证码字符源
   */
  public static String generateVerifyCode(int verifySize, String sources) {
    if (sources == null || sources.length() == 0) {
      sources = VERIFY_CODES;
    }
    int codesLen = sources.length();
    Random rand = new Random(System.currentTimeMillis());
    StringBuilder verifyCode = new StringBuilder(verifySize);
    for (int i = 0; i < verifySize; i++) {
      verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
    }
    return verifyCode.toString();
  }

  /**
   * 生成随机验证码文件,并返回验证码值
   */
  public static String outputVerifyImage(int w, int h, File outputFile, int verifySize)
      throws IOException {
    String verifyCode = generateVerifyCode(verifySize);
    outputImage(w, h, outputFile, verifyCode);
    return verifyCode;
  }

  /**
   * 输出随机验证码图片流,并返回验证码值
   */
  public static String outputVerifyImage(int w, int h, OutputStream os, int verifySize)
      throws IOException {
    String verifyCode = generateVerifyCode(verifySize);
    outputImage(w, h, os, verifyCode);
    return verifyCode;
  }

  /**
   * 生成指定验证码图像文件
   */
  public static void outputImage(int w, int h, File outputFile, String code) throws IOException {
    if (outputFile == null) {
      return;
    }
    File dir = outputFile.getParentFile();
    if (!dir.exists()) {
      dir.mkdirs();
    }
    try {
      outputFile.createNewFile();
      FileOutputStream fos = new FileOutputStream(outputFile);
      outputImage(w, h, fos, code);
      fos.close();
    } catch (IOException e) {
      throw e;
    }
  }

  /**
   * 输出指定验证码图片流
   */
  public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
    int verifySize = code.length();
    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Random rand = new Random();
    Graphics2D g2 = image.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    Color[] colors = new Color[5];
    Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN,
        Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
        Color.PINK, Color.YELLOW};
    float[] fractions = new float[colors.length];
    for (int i = 0; i < colors.length; i++) {
      colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
      fractions[i] = rand.nextFloat();
    }
    Arrays.sort(fractions);

    g2.setColor(Color.GRAY);// 设置边框色
    g2.fillRect(0, 0, w, h);

    Color c = getRandColor(200, 250);
    g2.setColor(c);// 设置背景色
    g2.fillRect(0, 2, w, h - 4);

    //绘制干扰线
    Random random = new Random();
    g2.setColor(getRandColor(160, 200));// 设置线条的颜色
    for (int i = 0; i < 20; i++) {
      int x = random.nextInt(w - 1);
      int y = random.nextInt(h - 1);
      int xl = random.nextInt(6) + 1;
      int yl = random.nextInt(12) + 1;
      g2.drawLine(x, y, x + xl + 40, y + yl + 20);
    }

    // 添加噪点
    float yawpRate = 0.05f;// 噪声率
    int area = (int) (yawpRate * w * h);
    for (int i = 0; i < area; i++) {
      int x = random.nextInt(w);
      int y = random.nextInt(h);
      int rgb = getRandomIntColor();
      image.setRGB(x, y, rgb);
    }

    shear(g2, w, h, c);// 使图片扭曲

    g2.setColor(getRandColor(100, 160));
    int fontSize = h - 4;
    Font font = new Font("Algerian", Font.ITALIC, fontSize);
    g2.setFont(font);
    char[] chars = code.toCharArray();
    for (int i = 0; i < verifySize; i++) {
      AffineTransform affine = new AffineTransform();
      affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
          (w / verifySize) * i + fontSize / 2, h / 2);
      g2.setTransform(affine);
      g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
    }

    g2.dispose();
    ImageIO.write(image, "jpg", os);
  }

  private static Color getRandColor(int fc, int bc) {
    if (fc > 255) {
      fc = 255;
    }
    if (bc > 255) {
      bc = 255;
    }
    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
  }

  private static int getRandomIntColor() {
    int[] rgb = getRandomRgb();
    int color = 0;
    for (int c : rgb) {
      color = color << 8;
      color = color | c;
    }
    return color;
  }

  private static int[] getRandomRgb() {
    int[] rgb = new int[3];
    for (int i = 0; i < 3; i++) {
      rgb[i] = random.nextInt(255);
    }
    return rgb;
  }

  private static void shear(Graphics g, int w1, int h1, Color color) {
    shearX(g, w1, h1, color);
    shearY(g, w1, h1, color);
  }

  private static void shearX(Graphics g, int w1, int h1, Color color) {

    int period = random.nextInt(2);

    boolean borderGap = true;
    int frames = 1;
    int phase = random.nextInt(2);

    for (int i = 0; i < h1; i++) {
      double d = (double) (period >> 1)
          * Math.sin((double) i / (double) period
          + (6.2831853071795862D * (double) phase)
          / (double) frames);
      g.copyArea(0, i, w1, 1, (int) d, 0);
      if (borderGap) {
        g.setColor(color);
        g.drawLine((int) d, i, 0, i);
        g.drawLine((int) d + w1, i, w1, i);
      }
    }

  }

  private static void shearY(Graphics g, int w1, int h1, Color color) {

    int period = random.nextInt(40) + 10; // 50;

    boolean borderGap = true;
    int frames = 20;
    int phase = 7;
    for (int i = 0; i < w1; i++) {
      double d = (double) (period >> 1)
          * Math.sin((double) i / (double) period
          + (6.2831853071795862D * (double) phase)
          / (double) frames);
      g.copyArea(i, 0, 1, h1, 0, (int) d);
      if (borderGap) {
        g.setColor(color);
        g.drawLine(i, (int) d, i, 0);
        g.drawLine(i, (int) d + h1, i, h1);
      }

    }

  }

  public static void main(String[] args) throws IOException {
    /*File dir = new File("c:/v");
    int w = 200, h = 80;
    for (int i = 0; i < 50; i++) {
      String verifyCode = generateVerifyCode(4);
      File file = new File(dir, verifyCode + ".jpg");
      outputImage(w, h, file, verifyCode);
    }*/
    System.out.println(checkPwd("0000000a"));
  }

  /**
   * 验证手机格式
   */
  public static boolean checkTeleTrue(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
    String telRegex = "[1][345678]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
    if (StringUtils.isEmpty(mobiles)){
      return false;
    }
    else {
      return mobiles.matches(telRegex);
    }
  }

  public static boolean checkPwd(String pwd) {
    String telRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)(?!([^(0-9a-zA-Z)]|[\\\\(\\\\)])+$)([^(0-9a-zA-Z)]|[\\\\(\\\\)]|[a-zA-Z]|[0-9]){8,16}$";
    //String telRegex ="^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?![,\\.#%'\\+\\*\\-:;^_`]+$)[,\\.#%'\\+\\*\\-:;^_`0-9A-Za-z]{8,16}$";
    if (StringUtils.isEmpty(pwd)){
      return false;
    }
    else {
      return pwd.matches(telRegex);
    }
  }

  /**
   * 验证邮箱
   */
  public static boolean checkEmailTrue(String email) {
    String emailRegex = "^([a-z0-9A-Z]+[-|_|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    if (StringUtils.isEmpty(email)){
      return false;
    }
    else {
      return email.matches(emailRegex);
    }
  }

  /**
   * 验证中文名字2-4位
   */
  public static boolean checkChineseNameTrue(String name) {
    String nameRegex = "[\\u4E00-\\u9FA5]{2,5}(?:·[\\u4E00-\\u9FA5]{2,5})*";

    if (StringUtils.isEmpty(name)){
      return false;
    }
    else {
      return name.matches(nameRegex);
    }
  }

  /**
   * 6 位数密码并且不能6位顺增\顺降\重复
   */
  public static boolean checkTransPwdTrue(String pwd) {
    String transRegex = "^[0-9]*$";
    String straight = "(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){5}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){5})\\d";
    String duplicate = "([\\d])\\1{5,}";

    if (StringUtils.isEmpty(pwd) || pwd.length() != 6){
      return false;
    }
    if(!pwd.matches(transRegex) || pwd.matches(straight) || pwd.matches(duplicate)){
      return false;
    }
    return true;
  }

  /**
   * 身份证校验
   */
  public static boolean checkIdCardTrue(String idStr) {
    String idCardRegex = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

    if (StringUtils.isEmpty(idStr)){
      return false;
    } else {
      return idStr.matches(idCardRegex) && idStr.length() == 18;
    }
  }

}
