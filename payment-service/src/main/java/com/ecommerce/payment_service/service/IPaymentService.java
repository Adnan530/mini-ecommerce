package com.ecommerce.payment_service.service;

import com.ecommerce.payment_service.event.PaymentEvent;
import java.util.UUID;

public interface IPaymentService {
    PaymentEvent processPayment(UUID orderId);

}
