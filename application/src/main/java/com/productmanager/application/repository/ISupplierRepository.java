package com.productmanager.application.repository;

import com.productmanager.application.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findSupplierByEmail(String email);
}
