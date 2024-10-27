package com.productmanager.application.service.impl;

import com.productmanager.application.exception.ResourceNotFoundException;
import com.productmanager.application.model.entity.Product;
import com.productmanager.application.repository.IProductRepository;
import com.productmanager.application.repository.ISupplierRepository;
import com.productmanager.application.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ISupplierRepository supplierService;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Product addProduct(Product product, Long supplierId) {
        var supplier = supplierService.
                findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", supplierId));

        var newProduct = new Product();

        newProduct.setCode(product.getCode());
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setStock(product.getStock());
        newProduct.setImageName(product.getImageName());
        newProduct.setStatus(product.getStatus());
        newProduct.setSupplier(supplier);

        product.setSupplier(supplier);

        return productRepository.save(newProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        var product = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        productRepository.delete(product);
    }

    @Override
    public void updateProduct(Long id, Product product) {
        var productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        if (productEntity.getCode().equals(product.getCode())) {
            productEntity.setCode(product.getCode());
        }
        if (productEntity.getName().equals(product.getName())) {
            productEntity.setName(product.getName());
        }
        if (productEntity.getDescription().equals(product.getDescription())) {
            productEntity.setDescription(product.getDescription());
        }
        if (productEntity.getPrice().equals(product.getPrice())) {
            productEntity.setPrice(product.getPrice());
        }
        if (productEntity.getStock().equals(product.getStock())) {
            productEntity.setStock(product.getStock());
        }
        if (productEntity.getImageName().equals(product.getImageName())) {
            productEntity.setImageName(product.getImageName());
        }
        if (productEntity.getStatus().equals(product.getStatus())) {
            productEntity.setStatus(product.getStatus());
        }

        productRepository.save(productEntity);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @Override

    public List<Product> getProductsByKey(String searchKey) {
        List<Product> products = productRepository.findProductsByKey(searchKey);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product", "searchKey", searchKey);
        }
        return products;
    }


    @Override
    public List<Product> getProductsBySupplierId(Long supplierId) {
        return productRepository.findProductsBySupplier_Id(supplierId);
    }
}
