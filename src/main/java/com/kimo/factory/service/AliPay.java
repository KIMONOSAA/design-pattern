package com.kimo.factory.service;

import com.kimo.enums.PayEnum;
import com.kimo.factory.abs.AbstractPaymentFactory;
import org.springframework.stereotype.Service;


@Service
public class AliPay extends AbstractPaymentFactory {
    @Override
    public void pay(double money) {
        System.out.println("使用支付宝支付 " + money + " 元");
    }

    @Override
    public PayEnum getHandleStrategy() {
        return PayEnum.ALIPAY;
    }
}
