package com.productmanager.application.mappers;

import com.productmanager.application.dto.SupplierDto;
import com.productmanager.application.model.entity.Supplier;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface SupplierMapper {
    SupplierDto supplierToSupplierDto(Supplier supplier);

    Supplier supplierDtoToSupplier(SupplierDto supplierDto);
}
