package com.kimo.enums;

public enum PayEnum {
    WXPAY(1), ALIPAY(2), UNIONPAY(3);


    private final int value;

    PayEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PayEnum fromValue(int value) {
        for (PayEnum pay : PayEnum.values()) {
            if (pay.getValue() == value) {
                return pay;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

}