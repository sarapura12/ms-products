package com.productmanager.application.repository;

import com.productmanager.application.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISupplierRepository extends JpaRepository<Supplier, Long> {
}
