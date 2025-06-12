package com.ecommerce.product_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GeneralResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;
}
