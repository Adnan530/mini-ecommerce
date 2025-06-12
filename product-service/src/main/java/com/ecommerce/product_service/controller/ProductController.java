package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.GeneralResponse;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GeneralResponse<ProductResponse> create(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse=productService.create(productRequest);
        return GeneralResponse.<ProductResponse>builder()
                .message("Product created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(productResponse)
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse<ProductResponse> update(@PathVariable UUID id,
                                          @Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse=productService.update(id, productRequest);
        return GeneralResponse.<ProductResponse>builder()
                .message("Product updated successfully")
                .statusCode(HttpStatus.OK.value())
                .data(productResponse)
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse<ProductResponse> getById(@PathVariable UUID id) {
        ProductResponse productResponse = productService.getById(id);
        return GeneralResponse.<ProductResponse>builder()
                .message("Product fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(productResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse<String> deleteById(@PathVariable UUID id) {
        productService.deleteById(id);
        return GeneralResponse.<String>builder()
                .message("Product deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse<List<ProductResponse>> getAll() {
        List<ProductResponse> responseList = productService.getAll();
        return GeneralResponse.<List<ProductResponse>>builder()
                .message("All products fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(responseList)
                .build();
    }

    @GetMapping
    public GeneralResponse<Page<ProductResponse>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean available,
            @PageableDefault(size = 10, sort = "name") Pageable pageable
    ) {
        Page<ProductResponse> products = productService.getProducts(name, category, available, pageable);
        return GeneralResponse.<Page<ProductResponse>>builder()
                .message("Product fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(products)
                .build();
    }

}
