package com.ecommerce.product_service.mapper;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper PRODUCT_MAPPER= Mappers.getMapper(ProductMapper.class);

    Product mapRequestToProduct(ProductRequest productRequest);
    ProductResponse mapProductToResponse(Product product);
}
