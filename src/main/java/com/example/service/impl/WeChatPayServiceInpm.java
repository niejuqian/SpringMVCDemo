package com.example.service.impl;

import com.example.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/1.
 * 微信支付
 */
@Service(value = "wechatPay")
public class WeChatPayServiceInpm implements PayService {
    private Logger logger = LoggerFactory.getLogger(WeChatPayServiceInpm.class);
    @Override
    public void pay() {
        logger.info("===========================使用微信支付");
    }
}
