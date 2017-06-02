package com.example.service.impl;

import com.example.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/1.
 * 支付宝支付
 */
@Service(value = "alipay")
public class AliPayServiceImpl implements PayService {
    private Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);
    @Override
    public void pay() {
        logger.info("===========================使用支付宝支付");
    }
}
