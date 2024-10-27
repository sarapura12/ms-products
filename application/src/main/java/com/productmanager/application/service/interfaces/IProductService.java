package com.productmanager.application.service.interfaces;

import com.productmanager.application.model.entity.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product, Long supplierId);

    void deleteProduct(Long id);

    void updateProduct(Long id, Product product);

    Product getProductById(Long id);

    List<Product> getProductsBySupplierId(Long supplierId);

    List<Product> getProductsByKey(String searchKey);
}
