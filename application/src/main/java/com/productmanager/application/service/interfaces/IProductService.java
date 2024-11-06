package com.productmanager.application.service.interfaces;

import com.productmanager.application.dto.ProductDto;
import com.productmanager.application.model.entity.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(ProductDto product);

    void deleteProduct(Long id);

    Product updateProduct(ProductDto product);

    Product getProductById(Long id);

    List<Product> getProductsBySupplierId(Long supplierId);

    List<Product> getProductsByKey(String searchKey);

    void discountProduct(Long id, Integer quantity);
}
