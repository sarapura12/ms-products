package com.productmanager.application.service.interfaces;

import com.productmanager.application.dto.SupplierDto;
import com.productmanager.application.model.entity.Supplier;

public interface ISupplierService {
    Supplier createSupplier(SupplierDto supplierDto);

    Supplier updateSupplier(SupplierDto supplierDto);

    void deleteSupplier(Long id);

    Supplier getSupplierById(Long id);
}
