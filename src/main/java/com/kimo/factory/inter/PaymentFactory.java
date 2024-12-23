package com.kimo.factory.inter;

import com.kimo.enums.PayEnum;

public interface PaymentFactory {
    void pay(double money);

    PayEnum getHandleStrategy();
}
