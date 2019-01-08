package com.hhh.quality.platform.response;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseModel extends HashMap<String, Object> {

  private static final Logger logger = LoggerFactory.getLogger(ResponseModel.class);

  private static final long serialVersionUID = 4672696204727508229L;

  private final static String CODE = "code";
  private final static String MSG = "msg";
  private final static String DATA = "data";


  public static ResponseModel getModel(ResponseCode respCode) {
    ResponseModel model = new ResponseModel();
    model.put(CODE, respCode.getCode());
    model.put(MSG, respCode.getMsg());
    return model;
  }

  public static ResponseModel getModel(ResponseCode respCode, String msg) {
    ResponseModel model = getModel(respCode);
    model.put(MSG, msg);
    return model;
  }

  public static ResponseModel getModel(ResponseCode respCode, Object data) {
    ResponseModel model = getModel(respCode);
    model.put("data", data);
    return model;
  }

  public static ResponseModel getModel(ResponseCode respCode, String msg, Object data) {
    ResponseModel model = getModel(respCode);
    model.put(MSG, msg);
    model.put("data", data);
    return model;
  }

  public static ResponseModel ok() {
    return getModel(ResponseCode.API_OK);
  }

  public static ResponseModel ok(Object data) {
    return getModel(ResponseCode.API_OK, data);
  }

  public static ResponseModel error(String msg) {
    return getModel(ResponseCode.API_99999).setMsg(msg);
  }

  public static ResponseModel error(ResponseCode respCode) {
    return getModel(respCode);
  }

  public String getMsg() {
    return String.valueOf(this.get("msg"));
  }

  public ResponseModel setMsg(String msg) {
    this.put(MSG, msg);
    return this;
  }

  public ResponseModel setCode(ResponseCode code) {
    this.put(CODE, code.getCode());
    return this;
  }

  public Integer getCode() {
    Object code = get(CODE);

    return code != null ? (int) code : null;
  }

  public ResponseModel setData(Object data) {
    put("data", data);
    return this;
  }

  public Map getData() {
    return (Map) get("data");
  }

  public ResponseModel setData(String key, Object val) {
    Map data = getData();
    if (data == null) {
      data = new HashMap(1);
    }
    data.put(key, val);
    this.put(DATA, data);
    return this;
  }
}
