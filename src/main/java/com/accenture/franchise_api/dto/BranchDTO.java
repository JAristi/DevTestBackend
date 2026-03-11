package com.accenture.franchise_api.dto;

import lombok.Data;

@Data
public class BranchDTO {

    private Long id;
    private String name;
    private Long franchiseId;
}
