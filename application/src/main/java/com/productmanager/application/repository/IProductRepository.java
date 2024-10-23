package com.productmanager.application.repository;

import com.productmanager.application.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "p.code LIKE %:searchKey% OR " +
            "p.name LIKE %:searchKey% OR " +
            "p.description LIKE %:searchKey%")
    List<Product> searchProducts(@Param("searchKey") String searchKey);
}
