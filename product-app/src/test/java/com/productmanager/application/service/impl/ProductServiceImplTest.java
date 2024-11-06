package com.productmanager.application.service.impl;

import com.productmanager.application.dto.ProductDto;
import com.productmanager.application.exception.ResourceNotFoundException;
import com.productmanager.application.model.entity.Product;
import com.productmanager.application.model.entity.Supplier;
import com.productmanager.application.model.enums.ProductStatus;
import com.productmanager.application.repository.IProductRepository;
import com.productmanager.application.repository.ISupplierRepository;
import org.junit.jupiter.api.AfterEach;
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
    private ProductDto productDto;
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

        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setCode("P002");
        productDto.setName("Product 1");
        productDto.setDescription("Description modify");
        productDto.setPrice(200.0);
        productDto.setStock(0);
        productDto.setImageName("image2.png");
        productDto.setStatus(ProductStatus.AVAILABLE);
        productDto.setSupplierId(1L);
    }

    @AfterEach
    void tearDown() {
        product = null;
        productDto = null;
        supplier = null;
    }

    @Test
    void addProduct_Success() {

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.addProduct(productDto);

        assertNotNull(createdProduct);
        assertEquals("Product 1", createdProduct.getName());
        assertEquals(200.0, createdProduct.getPrice());
        assertEquals(0, createdProduct.getStock());
        assertEquals("image2.png", createdProduct.getImageName());
        assertEquals(ProductStatus.AVAILABLE, createdProduct.getStatus());
        assertEquals(1L, createdProduct.getSupplier().getId());

        verify(supplierRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void addProduct_SupplierNotFound() {
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.addProduct(productDto);
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
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(any());


        productService.updateProduct(productDto);

        assertEquals("Product 1", product.getName());
        assertEquals(200.0, product.getPrice());
        assertEquals(0, product.getStock());
        assertEquals("image2.png", product.getImageName());
        assertEquals(ProductStatus.AVAILABLE, product.getStatus());
        assertEquals(1L, product.getSupplier().getId());

        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(productDto);
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