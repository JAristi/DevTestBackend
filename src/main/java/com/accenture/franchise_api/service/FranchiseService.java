package com.accenture.franchise_api.service;

import com.accenture.franchise_api.dto.TopProductDTO;
import com.accenture.franchise_api.entity.Branch;
import com.accenture.franchise_api.entity.Franchise;
import com.accenture.franchise_api.entity.Product;

import java.util.List;

public interface FranchiseService {

    Franchise createFranchise(String name);

    Franchise updateFranchiseName(Long id, String name);

    Branch addBranch(Long franchiseId, String name);

    Branch updateBranchName(Long id, String name);

    Product addProduct(Long branchId, String name, Integer stock);

    Product updateProductName(Long id, String name);

    Product updateStock(Long productId, Integer stock);

    void deleteProduct(Long franchiseId, Long branchId, Long productId);

    List<TopProductDTO> getTopProducts(Long franchiseId);
}
