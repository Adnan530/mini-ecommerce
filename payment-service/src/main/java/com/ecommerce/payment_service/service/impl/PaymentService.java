package com.ecommerce.payment_service.service.impl;

import com.ecommerce.payment_service.event.PaymentEvent;
import com.ecommerce.payment_service.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService implements IPaymentService {

    @Override
    public PaymentEvent processPayment(UUID orderId) {
        return new PaymentEvent(orderId, true, "Payment processed successfully");
    }

}
