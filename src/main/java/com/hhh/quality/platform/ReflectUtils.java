package com.hhh.quality.platform;
/**
 * @(#)ReflectUtils.java Copyright 2012 jointown, Inc. All rights reserved.
 */


import java.lang.reflect.Field;

/**
 * @date 2012-11-16 反射工具类
 */
public class ReflectUtils {

  /**
   * 设置obj对象fieldName的属性值
   */
  public static void setValueByFieldName(Object obj, String fieldName, Object value) {
    try {
      Field field = obj.getClass().getDeclaredField(fieldName);
      if (field.isAccessible()) {
        field.set(obj, value);
      } else {
        field.setAccessible(true);
        field.set(obj, value);
        field.setAccessible(false);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取obj对象fieldName的属性值
   */
  public static Object getValueByFieldName(Object obj, String fieldName) throws SecurityException,
      NoSuchFieldException,
      IllegalArgumentException,
      IllegalAccessException {
    Field field = getFieldByFieldName(obj, fieldName);
    Object value = null;
    if (field != null) {
      if (field.isAccessible()) {
        value = field.get(obj);
      } else {
        field.setAccessible(true);
        value = field.get(obj);
        field.setAccessible(false);
      }
    }
    return value;
  }

  /**
   * 获取obj对象fieldName的Field
   */
  public static Field getFieldByFieldName(Object obj, String fieldName) {
    for (Class<?> superClass = obj.getClass(); superClass != Object.class;
        superClass = superClass.getSuperclass()) {
      try {
        return superClass.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
      }
    }
    return null;
  }
}
