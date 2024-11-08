package com.productmanager.application.controller;

import com.productmanager.application.dto.SupplierDto;
import com.productmanager.application.mappers.SupplierMapper;
import com.productmanager.application.model.entity.Supplier;
import com.productmanager.application.service.interfaces.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final ISupplierService supplierService;
    @Autowired
    private SupplierMapper supplierMapper;

    public SupplierController(ISupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable Long id) {
        var supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplierMapper.supplierToSupplierDto(supplier));
    }

    @PostMapping
    public ResponseEntity<SupplierDto> createSupplier(@RequestBody SupplierDto supplierDto) {
        Supplier supplier = supplierService.createSupplier(supplierDto);
        var supplierMap = supplierMapper.supplierToSupplierDto(supplier);
        return new ResponseEntity<>(supplierMap, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SupplierDto> updateSupplier(@RequestBody SupplierDto supplierDto) {
        Supplier supplier = supplierService.updateSupplier(supplierDto);
        return ResponseEntity.ok(supplierMapper.supplierToSupplierDto(supplier));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}