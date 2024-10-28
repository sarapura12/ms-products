package com.productmanager.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDto {

    private Long id;
    private String name;
    private String email;
    private String phone;

    public SupplierDto(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public SupplierDto() {
    }
}
