package com.changgou.pay.service.impl;

import com.changgou.pay.service.WXPayService;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import entity.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class WXPayServiceImpl implements WXPayService {


    String key = "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";


    @Value("${wxpay.notify_url}")
    private String notify_url;

    //统一下单接口调用
    @Override
    public Map nativePay(String orderId, String money) throws Exception {

        //1. 封装请求参数
        Map<String, String> map = new HashMap<>();
        map.put("appid", "wx8397f8696b538317");
        map.put("mch_id", "1473426802");
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("body", "畅购");
        map.put("out_trade_no", orderId);
        map.put("spbill_create_ip", "127.0.0.1");
        map.put("notify_url", notify_url);
        map.put("trade_type", "NATIVE");
        map.put("total_fee", money);
        String A = WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb");
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        HttpClient httpClient = new HttpClient(url);
        httpClient.setHttps(true);
        httpClient.setXmlParam(A);
        httpClient.post();
        String respData = httpClient.getContent();
        Map<String, String> respDat = WXPayUtil.xmlToMap(respData);
//            map.put("body","畅购");
//            map.put("out_trade_no",orderId);
//            BigDecimal payMoney = new BigDecimal("0.01");
//            BigDecimal fen = payMoney.multiply(new BigDecimal("100")); //1.00
//            fen = fen.setScale(0,BigDecimal.ROUND_UP); // 1
//            map.put("total_fee",money);
//
//            map.put("spbill_create_ip","127.0.0.1");
//            map.put("notify_url",notify_url);
//            map.put("trade_type","NATIVE");


        return respDat;

    }

    @Override
    public Map queryOrder(String orderId) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("appid", "wx8397f8696b538317");
        map.put("mch_id", "1473426802");
        map.put("nonce_str", WXPayUtil.generateNonceStr());

        map.put("out_trade_no", orderId);

        String A = WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb");
        String url = "https://api.mch.weixin.qq.com/pay/orderquery";
        HttpClient httpClient = new HttpClient(url);
        httpClient.setHttps(true);
        httpClient.setXmlParam(A);
        httpClient.post();
        String respData = httpClient.getContent();
        Map<String, String> respDat = WXPayUtil.xmlToMap(respData);
        return respDat;

    }

}
