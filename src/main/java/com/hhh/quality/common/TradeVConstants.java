package com.hhh.quality.common;

/**
 * 静态常量常量
 */
public class TradeVConstants {

  public static final String USDT_PRICE = "PRICE:USDT_PRICE";
  public static final String BTC_PRICE = "PRICE:BTC_PRICE";
  public static final String ETH_PRICE = "PRICE:ETH_PRICE";

  /**
   * 订单key 序列
   */
  public static final String TRADDE_ORDER = "ORDER:";

  /**
   * 一个星期
   */
  public static final String TRADDE_1W = "KLINE:TRADDE_1WEEK:";

  /**
   * 1天线
   */
  public static final String TRADDE_1D = "KLINE:TRADDE_1DAY:";

  /**
   * 1小时线
   */
  public static final String TRADDE_1H = "KLINE:TRADDE_1HOUR:";

  /**
   * 1分钟线
   */
  public static final String TRADDE_1M = "KLINE:TRADDE_1MINUE:";

  /**
   * 5分钟线
   */
  public static final String TRADDE_5M = "KLINE:TRADDE_5MINUE:";

  /**
   * 15分钟线
   */
  public static final String TRADDE_15M = "KLINE:TRADDE_15MINUE:";

  /**
   * 30分钟线
   */
  public static final String TRADDE_30M = "KLINE:TRADDE_30MINUE:";

  /**
   * 货币对行情
   */
  public static final String TRADDE_MARKET = "MARKET:TRADDE_MARKET:";

  /**
   * 货币对 最新价格 兑率
   */
  public static final String TRADDE_MARKET_PRICE = "MARKET:TRADDE_MARKET_PRICE:";

  /**
   * 交易委托买
   */
  public static final String TRADDE_ENTRUST_BUY = "ENTRUST:TRADDE_ENTRUST_BUY:";

  /**
   * 交易委托卖
   */
  public static final String TRADDE_ENTRUST_SELL = "ENTRUST:TRADDE_ENTRUST_SELL:";

  /**
   * 交易委托买
   */
  public static final String TRADDE_ENTRUST_HISTORY = "ENTRUST:TRADDE_ENTRUST_HISTORY:";

  public static String getKey(Long marketId, Long coinId){
    return MD5Utils.MD5(marketId + coinId + "");
  }


}
