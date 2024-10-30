package com.productmanager.application.service.impl;

import com.productmanager.application.dto.SupplierDto;
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
    public Supplier createSupplier(SupplierDto supplierDto) {
        var sup = supplierRepository.findSupplierByEmail(supplierDto.getEmail()).orElse(null);

        if (sup != null) {
            throw new InvalidOperationException("Supplier", supplierDto.getEmail(), "Email already exists");
        }

        var supplier = new Supplier();
        supplier.setName(supplierDto.getName());
        supplier.setEmail(supplierDto.getEmail());
        supplier.setPhone(supplierDto.getPhone());

        supplierRepository.save(supplier);
        return supplier;
    }

    @Override
    public Supplier updateSupplier(SupplierDto supplierDto) {
        var supplierEntity = supplierRepository
                .findById(supplierDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", supplierDto.getId()));

        if (!supplierEntity.getName().equals(supplierDto.getName())) {
            supplierEntity.setName(supplierDto.getName());
        }
        if (!supplierEntity.getEmail().equals(supplierDto.getEmail())) {
            supplierEntity.setEmail(supplierDto.getEmail());
        }
        if (!supplierEntity.getPhone().equals(supplierDto.getPhone())) {
            supplierEntity.setPhone(supplierDto.getPhone());
        }

        supplierRepository.save(supplierEntity);
        return supplierEntity;
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
