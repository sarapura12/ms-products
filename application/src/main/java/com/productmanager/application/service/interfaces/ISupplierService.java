package com.productmanager.application.service.interfaces;

import com.productmanager.application.model.entity.Supplier;

public interface ISupplierService {
    Supplier createSupplier(Supplier supplier);

    Supplier updateSupplier(Long id, Supplier supplier);

    void deleteSupplier(Long id);

    Supplier getSupplierById(Long id);
}
