package com.productmanager.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String status;
    private SupplierDto supplier;

    public ProductDto(Long id, String name, String description, Double price, Integer quantity, String status, SupplierDto supplier) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.supplier = supplier;
    }

    public ProductDto() {
    }
}
