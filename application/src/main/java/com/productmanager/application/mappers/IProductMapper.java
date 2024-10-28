package com.productmanager.application.mappers;

import com.productmanager.application.dto.ProductDto;
import com.productmanager.application.model.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface IProductMapper {
    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    List<ProductDto> productsToProductDtos(List<Product> productsByKey);
}
