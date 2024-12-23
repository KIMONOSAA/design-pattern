package factory;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kimo.DesignApplication;
import com.kimo.controller.PayController;
import com.kimo.enums.PayEnum;
import com.kimo.factory.abs.AbstractPaymentFactory;
import com.kimo.factory.abs.PaymentStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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