package com.productmanager.application.controller;

import com.productmanager.application.dto.SupplierDto;
import com.productmanager.application.mappers.ISupplierMapper;
import com.productmanager.application.model.entity.Supplier;
import com.productmanager.application.service.interfaces.ISupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final ISupplierService supplierService;
    private final ISupplierMapper supplierMapper;

    public SupplierController(ISupplierService supplierService, ISupplierMapper supplierMapper) {
        this.supplierService = supplierService;
        this.supplierMapper = supplierMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable Long id) {
        var supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplierMapper.supplierToSupplierDto(supplier));
    }

    @PostMapping
    public ResponseEntity<SupplierDto> createSupplier(@RequestBody SupplierDto supplierDto) {
        Supplier supplier = supplierService.createSupplier(supplierMapper.supplierDtoToSupplier(supplierDto));
        return new ResponseEntity<>(supplierMapper.supplierToSupplierDto(supplier), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable Long id, @RequestBody SupplierDto supplierDto) {
        Supplier supplier = supplierService.updateSupplier(id, supplierMapper.supplierDtoToSupplier(supplierDto));
        return ResponseEntity.ok(supplierMapper.supplierToSupplierDto(supplier));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}