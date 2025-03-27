package com.sz.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "stock")
@Data
public class Stock {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 @Column(name="product_name",unique = true,nullable = false)
    private String productName;
 @Column(name="stock",nullable = false)
    private Integer stock;
 @Version
    private Integer version;
}
