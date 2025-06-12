package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.exception.BusinessException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.IProductService;
import com.ecommerce.product_service.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    @Override
    @CacheEvict(value = "all_products", allEntries = true)
    public ProductResponse create(ProductRequest productRequest) {
        log.info("Creating new product: {}", productRequest);
        Product product = ProductMapper.PRODUCT_MAPPER.mapRequestToProduct(productRequest);
        product.setAvailable(true);
        product=productRepository.save(product);
        log.info("Product created with ID: {}", product.getId());
        return ProductMapper.PRODUCT_MAPPER.mapProductToResponse(product);
    }

    @Override
    public ProductResponse update(UUID id, ProductRequest productRequest) {
        log.info("Updating product with ID: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product not found with id: " + id));
        Product updatedProduct = ProductMapper.PRODUCT_MAPPER.mapRequestToProduct(productRequest);
        updatedProduct.setId(existingProduct.getId());
        updatedProduct.setAvailable(existingProduct.getAvailable());
        productRepository.save(updatedProduct);
        log.info("Product with ID: {} updated successfully", id);
        return ProductMapper.PRODUCT_MAPPER.mapProductToResponse(updatedProduct);
    }

    @Override
    @CacheEvict(value = {"products", "all_products"}, key = "#id", allEntries = true)
    public void deleteById(UUID id) {
        log.info("Deleting product by ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new BusinessException("Product not found with id: " + id);
                });

        product.setAvailable(false);
        productRepository.save(product);

        productRepository.deleteById(id);
        log.info("Product with ID: {} deleted successfully", id);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductResponse getById(UUID id) {
        log.info("Product by ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product not found with id: " + id));
        return ProductMapper.PRODUCT_MAPPER.mapProductToResponse(product);
    }

    @Override
    @Cacheable(value = "all_products")
    public List<ProductResponse> getAll() {
        log.info("Fetching all products");
        List<Product> influencers = productRepository.findAll();
        return influencers.stream()
                .map(ProductMapper.PRODUCT_MAPPER::mapProductToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponse> getProducts(String name, String category, Boolean available, Pageable pageable) {
        Specification<Product> spec = ProductSpecification.filter(name, category, available);
        return productRepository.findAll(spec, pageable)
                .map(ProductMapper.PRODUCT_MAPPER::mapProductToResponse);
    }
}
