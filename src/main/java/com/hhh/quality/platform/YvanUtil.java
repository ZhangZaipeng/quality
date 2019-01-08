package com.hhh.quality.platform;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hhh.quality.platform.platform.JsonWapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 转换类
 */
public class YvanUtil {

  public static final String VIEW_HISTORY_COOKIE_KEY = "client_view_history";
  public static final int COOKIE_AGE = 30 * 24 * 60 * 60;
  public static final String COOKIE_PATH = "/";
  private static ObjectMapper objectMapper;
  private static ResourceLoader loader = new DefaultResourceLoader();

  static {
    objectMapper = new ObjectMapper();
    objectMapper.writerWithDefaultPrettyPrinter();
  }

  public static JsonWapper merge(String mainNode, String updateNode) {
    try {
      return new JsonWapper(objectMapper.writeValueAsString(
          merge(objectMapper.readTree(mainNode), objectMapper.readTree(updateNode))));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static JsonNode merge(JsonNode mainNode, JsonNode updateNode) {

    Iterator<String> fieldNames = updateNode.fieldNames();
    while (fieldNames.hasNext()) {

      String fieldName = fieldNames.next();
      JsonNode mainJsonNode = mainNode.get(fieldName);
      JsonNode updateJsonNode = updateNode.get(fieldName);

      // if field exists and is an embedded object
      if (mainJsonNode != null && mainJsonNode.isObject()) {
        merge(mainJsonNode, updateNode.get(fieldName));
      } else {
        if (mainJsonNode != null && mainJsonNode.isArray()) {
          if (updateJsonNode != null) {
            ArrayNode arrayNode = (ArrayNode) mainJsonNode;
            if (updateJsonNode.isArray()) {
              //合并2个数组
              arrayNode.addAll((ArrayNode) updateJsonNode);
            } else {
              arrayNode.add(updateJsonNode);
            }
          }
        } else if (mainNode instanceof ObjectNode) {
          // Overwrite field
          JsonNode value = updateNode.get(fieldName);
          ((ObjectNode) mainNode).set(fieldName, value);
        }
      }

    }

    return mainNode;
  }

  public static String joinCollection(Iterable<?> iterable, String spt, String emptyString) {
    StringBuilder sb = new StringBuilder();
    String sp = "";
    Iterator<?> iter = iterable.iterator();

    while (iter.hasNext()) {
      Object o = iter.next();
      sb.append(sp).append(Conv.NS(o));
      sp = spt;
    }
    if (sb.length() <= 0) {
      return emptyString;
    } else {
      return sb.toString();
    }
  }

  public static String joinCollection(Iterable<?> iterable, String spt) {
    return joinCollection(iterable, spt, "");
  }

  public static <T> T mapToEntities(Object listMap, Class<T> clazz) {
    try {
      String jsonString = objectMapper.writeValueAsString(listMap);
      return objectMapper.readValue(jsonString, clazz);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将Json字符串序列化成指定对象
   */
  public static <T> T jsonToObj(String jsonStr, Class<T> clazz) {
    try {
      return objectMapper.readValue(jsonStr, clazz);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将Json字符串序列化成指定对象
   */
  public static <T> T jsonToObj(String jsonStr, TypeReference<T> ref) {
    try {
      return objectMapper.readValue(jsonStr, ref);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将Json字符串序列化成 list
   */
  public static List<?> jsonToList(String jsonInput) {
    try {
      return objectMapper.readValue(jsonInput, List.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将Json字符串序列化成json
   */
  public static Map<?, ?> jsonToMap(String jsonInput) {
    try {
      return objectMapper.readValue(jsonInput, Map.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将对象序列化成json
   */
  public static String toJson(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将对象序列化成json
   */
  public static String toJsonPretty(Object obj) {
    try {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T createInstance(Class<T> clazz, String classFullName, Object... args)
      throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
      InstantiationException {
    Constructor constructor = Class.forName(classFullName).getConstructors()[0];
    return (T) constructor.newInstance(args);
  }

  public static String encodeBase64(String s) {
    return Base64.getUrlEncoder().encodeToString(s.getBytes());
  }

  /**
   * 将 BASE64 编码的字符串 s 进行解码
   * @param s
   * @return
   */
  public static String decodeBase64(String s) {
    try {
      byte[] b = Base64.getDecoder().decode(s);
      return new String(b, "UTF-8");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 得到当前request请求的所有cookie
   *
   * @return cookie数组
   * @author jianguo.xu
   */
  public static Cookie[] getCookies() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes()).getRequest();
    return request == null ? null : request.getCookies();
  }

  /**
   * 根据cookie名字取得cookie
   *
   * @param name cookie名字
   * @return cookie
   * @author jianguo.xu
   */
  public static Cookie getCookie(String name) {
    Cookie[] cookies = getCookies();
    return getCookieByName(cookies,name);
  }

  public static Cookie getCookieByName(Cookie[] cookies, String name){
    if (cookies != null && cookies.length > 0) {
      for (int i = 0; i < cookies.length; i++) {
        Cookie cookie = cookies[i];
        String cookName = cookie.getName();
        if (cookName != null && cookName.equals(name)) {
          return cookie;
        }
      }
    }
    return null;
  }

  public static String getCookieValue(String name) {
    Cookie cookie = getCookie(name);
    if (cookie == null) {
      return null;
    }
    return cookie.getValue();
  }

  /**
   * 将cookie写入客户端
   *
   * @author jianguo.xu
   */
  public static void writeCookie(Cookie cookie) {
    if (cookie == null) {
      return;
    }
    ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes());
    HttpServletResponse response = attributes.getResponse();
    HttpServletRequest request = attributes.getRequest();
    /*
     * if (request != null) { String host = request.getHeader("Host"); if (ConfigUtils
     * .WEBSITE.equals(host))
     * cookie.setDomain("." + ConfigUtils.DOMAIN); }
     */
    if (response != null) {
      response.addCookie(cookie);
    }
  }

  public static void removeCookie(String cookieName, String path) {
    ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes());
    HttpServletResponse response = attributes.getResponse();
    HttpServletRequest request = attributes.getRequest();

    Cookie[] cookies = request.getCookies();
    if (cookies == null || cookies.length == 0) {
      return;
    }

    for (int i = 0; i < cookies.length; i++) {
      Cookie cookie = cookies[i];
      if (cookie.getName().equals(cookieName)) {
        cookie.setMaxAge(0);
        cookie.setPath(path);
        /*
         * String host = request.getHeader("Host"); if (ConfigUtils.WEBSITE.equals(host))
         * cookie.setDomain("." +
         * ConfigUtils.DOMAIN);
         */
        response.addCookie(cookie);
        break;
      }
    }

  }

  public static String urlEncoding(String value) {
    try {
      byte[] bs = Base64.getUrlEncoder().encode(value.getBytes("UTF-8"));
      return new String(bs, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("encode error.", e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> bean2Map(Object javaBean) {
    Map<K, V> ret = new HashMap<K, V>();
    try {
      Method[] methods = javaBean.getClass().getDeclaredMethods();
      for (Method method : methods) {
        if (method.getName().startsWith("get")) {
          String field = method.getName();
          field = field.substring(field.indexOf("get") + 3);
          field = field.toLowerCase().charAt(0) + field.substring(1);
          Object value = method.invoke(javaBean, (Object[]) null);
          ret.put((K) field, (V) (null == value ? "" : value));
        }
      }
    } catch (Exception e) {
    }
    return ret;
  }

  public static void main(String[] args) {

    System.out.println(YvanUtil.urlEncoding("http://192.168.0.249/JAVA/bcex-parent"));
  }
}
