package com.kimo.factory.abs;

import com.kimo.factory.inter.PaymentFactory;

public abstract class AbstractPaymentFactory implements PaymentFactory {

   public final void calculateMoney(double money){
       check(money);
       pay(money);

    }

    private void check(double amount) {
        if(amount==0){
            throw new IllegalArgumentException("无效参数!");
        }
        System.out.println("校验方法");
    }


}
