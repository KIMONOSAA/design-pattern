package com.kimo.factory.abs;

import com.kimo.enums.PayEnum;
import com.kimo.factory.inter.PaymentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//@Component
//public class PaymentStrategyFactory implements ApplicationContextAware {
//    ApplicationContext applicationContext;
//
//    public AbstractPaymentFactory getPaymentStrategy(int payType) {
//        switch (payType) {
//            case 1:
//                return applicationContext.getBean(WeChatPay.class);
//            case 2:
//                return applicationContext.getBean(AliPay.class);
//            case 3:
//                return applicationContext.getBean(UnionPay.class);
//            default:
//                throw new IllegalArgumentException("无效的支付类型");
//        }
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext=applicationContext;
//    }
//}


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