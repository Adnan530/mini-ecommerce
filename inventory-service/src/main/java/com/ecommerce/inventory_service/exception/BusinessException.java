package com.ecommerce.inventory_service.exception;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }
}

