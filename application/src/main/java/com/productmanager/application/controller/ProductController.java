package com.productmanager.application.controller;

import com.productmanager.application.dto.ProductDto;
import com.productmanager.application.mappers.IProductMapper;
import com.productmanager.application.model.entity.Product;
import com.productmanager.application.service.interfaces.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService productService;
    private final IProductMapper productMapper;

    public ProductController(IProductService productService, IProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        var product = productService.getProductById(id);

        return ResponseEntity.ok(productMapper.productToProductDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        Product product = productService.addProduct(productDto);
        return new ResponseEntity<>(productMapper.productToProductDto(product), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        Product product = productService.updateProduct(productDto);
        return ResponseEntity.ok(productMapper.productToProductDto(product));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> getProductsByKey(@RequestParam String searchKey) {
        List<Product> productsByKey = productService.getProductsByKey(searchKey);
        return ResponseEntity.ok(productMapper.productsToProductDtos(productsByKey));
    }

    @GetMapping("/supplier/{supplierId}")
    public List<Product> getProductsBySupplierId(@PathVariable Long supplierId) {
        return productService.getProductsBySupplierId(supplierId);
    }

}

