package com.productmanager.application.service.impl;

import com.productmanager.application.exception.ResourceNotFoundException;
import com.productmanager.application.model.entity.Product;
import com.productmanager.application.model.entity.Supplier;
import com.productmanager.application.model.enums.ProductStatus;
import com.productmanager.application.repository.IProductRepository;
import com.productmanager.application.repository.ISupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ISupplierRepository supplierRepository;

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        supplier = new Supplier();
        supplier.setId(1L);

        product = new Product();
        product.setId(1L);
        product.setCode("P001");
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(100.0);
        product.setStock(10);
        product.setImageName("image1.png");
        product.setStatus(ProductStatus.AVAILABLE);
        product.setSupplier(supplier);
    }

    @Test
    void addProduct_Success() {
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.addProduct(product, 1L);

        assertNotNull(createdProduct);
        assertEquals("Product 1", createdProduct.getName());
        verify(supplierRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void addProduct_SupplierNotFound() {
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.addProduct(product, 1L);
        });

        assertEquals("Supplier", exception.getResourceName());
        assertEquals("id", exception.getFieldName());
        assertEquals(1L, exception.getFieldValue());
        verify(supplierRepository, times(1)).findById(anyLong());
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void deleteProduct_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    void deleteProduct_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(1L);
        });

        assertEquals("Product", exception.getResourceName());
        assertEquals("id", exception.getFieldName());
        assertEquals(1L, exception.getFieldValue());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(0)).delete(any(Product.class));
    }

    @Test
    void updateProduct_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        product.setName("Updated Product");
        product.setPrice(200.0);

        productService.updateProduct(1L, product);

        assertEquals("Updated Product", product.getName());
        assertEquals(200.0, product.getPrice());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(1L, product);
        });

        assertEquals("Product", exception.getResourceName());
        assertEquals("id", exception.getFieldName());
        assertEquals(1L, exception.getFieldValue());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void getProductById_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getId());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(1L);
        });

        assertEquals("Product", exception.getResourceName());
        assertEquals("id", exception.getFieldName());
        assertEquals(1L, exception.getFieldValue());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    void getProductsBySupplierId_Success() {
        when(productRepository.findProductsBySupplier_Id(anyLong())).thenReturn(List.of(product));

        List<Product> products = productService.getProductsBySupplierId(1L);

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findProductsBySupplier_Id(anyLong());
    }
}