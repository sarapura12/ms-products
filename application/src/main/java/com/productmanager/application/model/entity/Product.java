package com.productmanager.application.model.entity;

import com.productmanager.application.model.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 250)
    private String code;
    @Column(length = 100)
    private String name;
    @Column(length = 250)
    private String description;
    private Double price;
    private Integer stock;
    @Column(length = 250)
    private String imageName;
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus Status;

    @ManyToOne
    private Supplier supplier;
}
