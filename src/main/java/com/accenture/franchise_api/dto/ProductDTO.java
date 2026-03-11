package com.accenture.franchise_api.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private Integer stock;
    private Long branchId;
}
