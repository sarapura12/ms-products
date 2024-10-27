package com.productmanager.application.mappers;

import com.productmanager.application.dto.SupplierDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ISupplierMapper {
    SupplierDto supplierToSupplierDto(SupplierDto supplier);

    SupplierDto supplierDtoToSupplier(SupplierDto supplierDto);
}
