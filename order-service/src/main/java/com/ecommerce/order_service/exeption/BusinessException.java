package com.ecommerce.order_service.exeption;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }
}
