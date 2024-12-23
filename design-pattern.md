# 前言

在当今软件开发中，系统的可扩展性和可维护性至关重要。随着业务的不断增长和需求的不断变化，软件系统必须能够灵活地应对功能的扩展，而不需要大规模的重构。特别是在涉及到多种行为和策略的场景下（例如支付方式、订单处理方式等），如果没有良好的设计，代码会变得臃肿，难以维护和扩展。

本文将通过结合 **抽象类、工厂模式和策略模式**，介绍如何设计一个灵活、可扩展且易于维护的系统。我们将以一个支付系统为例，展示如何利用这些设计模式来实现支付方式的管理，同时确保系统可以轻松地扩展支持新的支付方式。

为了有效应对支付方式的扩展需求，我们可以结合 **工厂模式** 和 **策略模式** 来设计系统。工厂模式将帮助我们封装支付方式的创建逻辑，而策略模式则允许我们根据不同的支付方式动态地切换支付策略。通过抽象类，我们可以确保支付方式之间的统一接口，从而使得系统更加灵活和易于维护。

# 目录结构

```
├─src                                                  
│  ├─main                                              
│  │  ├─java                                           
│  │  │  └─com                                         
│  │  │      └─kimo                                    
│  │  │          │  DesignApplication.java             
│  │  │          │                                     
│  │  │          ├─conf                                
│  │  │          ├─controller                          
│  │  │          │      PayController.java             
│  │  │          │                                     
│  │  │          ├─enums                               
│  │  │          │      PayEnum.java                   
│  │  │          │                                     
│  │  │          └─factory                             
│  │  │              ├─abs                             
│  │  │              │      AbstractPaymentFactory.java
│  │  │              │      PaymentStrategyFactory.java
│  │  │              │                                 
│  │  │              ├─inter                           
│  │  │              │      PaymentFactory.java        
│  │  │              │                                 
│  │  │              └─service                         
│  │  │                      AliPay.java               
│  │  │                      UnionPay.java
│  │  │                      WeChatPay.java
│  │  │
│  │  └─resources
│  └─test
│      └─java
│          └─factory
│                  PaymentControllerTest.java
│


```

# 实现代码

我们首先设计一个统一的支付接口，通过抽象类定义支付方式的通用方法。代码如下：

```java
//支付接口类
public interface PaymentFactory {
    void pay(double money);
}
```

```java
//抽象类工厂
public abstract class AbstractPaymentFactory implements PaymentFactory {
    
   public final void calculateMoney(double money){
       pay(money);

    }
}
```

然后设计各个支付方式来实现AbstractPaymentFactory这个抽象类工厂。代码如下：

```java
//service包下的支付宝方式支付
@Service
public class AliPay extends AbstractPaymentFactory {
    @Override
    public void pay(double money) {
        System.out.println("使用支付宝支付 " + money + " 元");
    }

}

//service包下的银联方式支付
@Service
public class UnionPay extends AbstractPaymentFactory {
    @Override
    public void pay(double money) {
        System.out.println("使用银联支付 " + money + " 元");
    }

}

//service包下的微信方式支付
@Service
public class WeChatPay extends AbstractPaymentFactory {
    @Override
    public void pay(double money) {
        System.out.println("使用微信支付 " + money + " 元");
    }

}
```

最后是策略模式在本文的应用，通过策略模式，使得我们可以在运行时灵活地选择支付方式，解耦支付逻辑。

首先实现枚举类，使用枚举类可以使代码更具有观赏性，代码如下：

```java
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
        throw new IllegalArgumentException("无效的值: " + value);
    }

}
```

在支付接口类加上getHandleStrategy()这个方法获取各个支付的枚举方式：

```java
public interface PaymentFactory {
    void pay(double money);

    PayEnum getHandleStrategy();
}
```

各个支付的实现代码如下：

```
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

@Service
public class UnionPay extends AbstractPaymentFactory {
    @Override
    public void pay(double money) {
        System.out.println("使用银联支付 " + money + " 元");
    }

    @Override
    public PayEnum getHandleStrategy() {
        return PayEnum.UNIONPAY;
    }
}

@Service
public class WeChatPay extends AbstractPaymentFactory {
    @Override
    public void pay(double money) {
        System.out.println("使用微信支付 " + money + " 元");
    }

    @Override
    public PayEnum getHandleStrategy() {
        return PayEnum.WXPAY;
    }
}
```

最后才是最关键的，使用hashMap来储存各个Bean的数据，这个利用Spring的Bean容器初始化机制，扫描各个支付服务类，将各个Bean类Put到hashMap中。

```java

//策略模式
@Service
public class PaymentStrategyFactory {

    //利用hashmap容器与spring特性将所有实现PaymentFactory的类扫描put到hashmap中
    public final ConcurrentHashMap<PayEnum,PaymentFactory> handlerStrategyMap = new ConcurrentHashMap<>();

    @Autowired
    public PaymentStrategyFactory(List<PaymentFactory> iHandlers){
        iHandlers.forEach(x -> handlerStrategyMap.put(x.getHandleStrategy(),x));
    }

    //根据key获取对应的PaymentFactory实现类
    public PaymentFactory getPayment(PayEnum payEnum){
        return handlerStrategyMap.get(payEnum);
    }

}
```

# 测试结果

代码如下：

```java
@SpringBootTest(classes = DesignApplication.class)
class PaymentControllerTest {

    @Autowired
    private PaymentStrategyFactory paymentStrategyFactory;

    @Test
    void testPayStrategy_Alipay() {
//        AbstractPaymentFactory paymentStrategy  = paymentStrategyFactory.getPaymentStrategy(1);
//        paymentStrategy.CalculateMoney(100);
        paymentStrategyFactory.getPayment(PayEnum.fromValue(1))
                .pay(100.0);
    }


}
```

![屏幕截图 2024-12-23 190941.png](https://p0-xtjj-private.juejin.cn/tos-cn-i-73owjymdk6/7bae0637b6ce4cbea8ebbfc1314328cd~tplv-73owjymdk6-jj-mark-v1:0:0:0:0:5o6Y6YeR5oqA5pyv56S-5Yy6IEAgUlZU:q75.awebp?policy=eyJ2bSI6MywidWlkIjoiODMzODQzNDI4MzMzMDY1In0%3D&rk3s=e9ecf3d6&x-orig-authkey=f32326d3454f2ac7e96d3d06cdbb035152127018&x-orig-expires=1735038596&x-orig-sign=4oRLBJC9XLVcR4Hgbm2uNZ5luNY%3D)

# GitHub

Github源代码地址：https://github.com/KIMONOSAA/design-pattern
