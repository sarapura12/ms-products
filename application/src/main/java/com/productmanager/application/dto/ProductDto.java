package com.productmanager.application.dto;

import com.productmanager.application.model.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private String imageName;
    private String code;
    private ProductStatus status;


    private Integer quantity;
    private Integer stock;

    private Long supplierId;
    private Double price;

    public ProductDto(
            Long id,
            String name,
            String description,
            Double price,
            Integer quantity,
            ProductStatus status,
            Long supplierId,
            String code,
            Integer stock,
            String imageName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.supplierId = supplierId;
        this.code = code;
        this.stock = stock;
        this.imageName = imageName;
        this.status = status;
    }

    public ProductDto() {
    }
}
