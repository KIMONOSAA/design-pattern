package com.kimo.controller;

import com.kimo.enums.PayEnum;
import com.kimo.factory.abs.PaymentStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    @Autowired
    public PaymentStrategyFactory paymentStrategyFactory;

    @RequestMapping("pay")
    public void pay(int payType, double amount) {
        paymentStrategyFactory.getPayment(PayEnum.fromValue(payType))
                .pay(amount);
    }


//    @RequestMapping("pay2")
//    public void payStrategy(int payType, double amount) {
//        AbstractPaymentFactory paymentStrategy  = paymentStrategyFactory.getPaymentStrategy(payType);
//        paymentStrategy.calculateMoney(amount);
//    }
}
