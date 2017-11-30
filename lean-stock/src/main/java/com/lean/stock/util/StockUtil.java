package com.lean.stock.util;

public class StockUtil
{
    public static String getSinaStockUrl(final String code) {
        final String url = "http://finance.sina.com.cn/realstock/company/";
        if (code == null) {
            return code;
        }
        if (code.startsWith("60")) {
            return url + "sh" + code + "/nc.shtml";
        }
        if (code.startsWith("00") || code.startsWith("30")) {
            return url + "sz" + code + "/nc.shtml";
        }
        return code;
    }
    
    public static String getEastStockUrl(final String code) {
        final String url = "http://quote.eastmoney.com/";
        if (code == null) {
            return code;
        }
        if (code.startsWith("60")) {
            return url + "sh" + code + ".html";
        }
        if (code.startsWith("00") || code.startsWith("30")) {
            return url + "sz" + code + ".html";
        }
        return code;
    }
}
