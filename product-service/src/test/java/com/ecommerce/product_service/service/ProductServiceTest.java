package com.ecommerce.product_service.service;


import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.entity.Product;
import com.ecommerce.product_service.exception.BusinessException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.impl.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private final UUID productId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductRequest request = new ProductRequest();
        request.setName("Product A");
        request.setCategory("Category A");
        request.setDescription("Basic desc");
        request.setPrice(BigDecimal.valueOf(100));
        request.setStockQuantity(10);

        Product product = ProductMapper.PRODUCT_MAPPER.mapRequestToProduct(request);
        product.setId(productId);
        product.setAvailable(true);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.create(request);
        assertEquals("Product A", response.getName());
        verify(productRepository).save(any(Product.class));
    }


    @Test
    void testUpdateProductSuccess() {
        ProductRequest request = new ProductRequest();
        request.setName("Updated Product");
        request.setCategory("Updated Category");
        request.setDescription("Updated Description");
        request.setStockQuantity(100);
        request.setPrice(BigDecimal.valueOf(299.99));

        Product existing = new Product();
        existing.setId(productId);
        existing.setName("Old Product");
        existing.setCategory("Old Category");
        existing.setAvailable(true);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = productService.update(productId, request);

        assertEquals("Updated Product", response.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testUpdateProductNotFound() {
        ProductRequest request = new ProductRequest();
        request.setName("Name");
        request.setCategory("Category");
        request.setDescription("Description");
        request.setStockQuantity(10);
        request.setPrice(BigDecimal.valueOf(49.99));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> productService.update(productId, request));
    }

    @Test
    void testDeleteById() {
        Product product = new Product();
        product.setId(productId);
        product.setName("To Delete");
        product.setCategory("Category");
        product.setAvailable(true);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteById(productId);

        verify(productRepository).save(product);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void testGetByIdSuccess() {
        Product product = new Product();
        product.setId(productId);
        product.setName("P1");
        product.setCategory("C1");
        product.setAvailable(true);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getById(productId);
        assertEquals("P1", response.getName());
    }

    @Test
    void testGetByIdNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> productService.getById(productId));
    }

    @Test
    void testGetAllProducts() {
        Product product = new Product();
        product.setId(productId);
        product.setName("P1");
        product.setCategory("C1");
        product.setAvailable(true);

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponse> responses = productService.getAll();

        assertEquals(1, responses.size());
        assertEquals("P1", responses.get(0).getName());
    }

}

