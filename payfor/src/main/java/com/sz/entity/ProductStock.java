package com.sz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStock {
    private Long id;
    private Long productId;
    private Integer stock;
}
