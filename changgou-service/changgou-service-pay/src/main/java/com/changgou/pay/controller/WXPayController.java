package com.changgou.pay.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.pay.config.RabbitMQConfig;
import com.changgou.pay.service.WXPayService;

import com.github.wxpay.sdk.WXPayUtil;
import entity.Result;
import entity.StatusCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/wxpay")
@RestController
public class WXPayController {

    @Autowired
    private WXPayService wxPayService;


    //下单
    @GetMapping("/nativePay")
    public Result nativePay(@RequestParam("orderId") String orderId, @RequestParam("money") String money) throws Exception {
        Map resultMap = wxPayService.nativePay(orderId, money);
        return new Result(true, StatusCode.OK, "", resultMap);
    }

    @RequestMapping("/notify")
    public Result notifyLogic(@RequestParam("orderId") String orderId) throws Exception {
        Map resultMap = wxPayService.queryOrder(orderId);
        return new Result(true, StatusCode.OK, "", resultMap);
    }


}
