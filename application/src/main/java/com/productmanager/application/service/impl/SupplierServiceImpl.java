package com.productmanager.application.service.impl;

import com.productmanager.application.exception.InvalidOperationException;
import com.productmanager.application.exception.ResourceNotFoundException;
import com.productmanager.application.model.entity.Supplier;
import com.productmanager.application.repository.ISupplierRepository;
import com.productmanager.application.service.interfaces.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    private ISupplierRepository supplierRepository;


    @Override
    public Supplier createSupplier(Supplier supplier) {
        supplierRepository
                .findSupplierByEmail(supplier.getEmail())
                .orElseThrow(() -> new InvalidOperationException("Supplier", supplier.getEmail(), "Email already exists"));

        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Long id, Supplier supplier) {
        var supplierEntity = supplierRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));

        if (supplierEntity.getName().equals(supplier.getName())) {
            supplierEntity.setName(supplier.getName());
        }
        if (supplierEntity.getEmail().equals(supplier.getEmail())) {
            supplierEntity.setEmail(supplier.getEmail());
        }
        if (supplierEntity.getPhone().equals(supplier.getPhone())) {
            supplierEntity.setPhone(supplier.getPhone());
        }

        return supplierRepository.save(supplierEntity);
    }

    @Override
    public void deleteSupplier(Long id) {
        var supplier = supplierRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));

        supplierRepository.delete(supplier);
    }

    @Override
    public Supplier getSupplierById(Long id) {
        return supplierRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
    }
}
