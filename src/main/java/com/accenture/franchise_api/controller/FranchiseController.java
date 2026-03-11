package com.accenture.franchise_api.controller;

import com.accenture.franchise_api.dto.TopProductDTO;
import com.accenture.franchise_api.entity.Branch;
import com.accenture.franchise_api.entity.Franchise;
import com.accenture.franchise_api.entity.Product;
import com.accenture.franchise_api.service.FranchiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;

    @PostMapping
    public Franchise createFranchise(@RequestParam String name) {
        return franchiseService.createFranchise(name);
    }

    @PutMapping("/{id}")
    public Franchise updateFranchise(@PathVariable Long id,
                                     @RequestParam String name) {
        return franchiseService.updateFranchiseName(id, name);
    }

    @PostMapping("/{franchiseId}/branches")
    public Branch addBranch(@PathVariable Long franchiseId,
                            @RequestParam String name) {
        return franchiseService.addBranch(franchiseId, name);
    }

    @PutMapping("/branches/{id}")
    public Branch updateBranch(@PathVariable Long id,
                               @RequestParam String name) {
        return franchiseService.updateBranchName(id, name);
    }

    @PostMapping("/branches/{branchId}/products")
    public Product addProduct(@PathVariable Long branchId,
                              @RequestParam String name,
                              @RequestParam Integer stock) {
        return franchiseService.addProduct(branchId, name, stock);
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestParam String name) {
        return franchiseService.updateProductName(id, name);
    }

    @PutMapping("/products/{productId}/stock")
    public Product updateStock(@PathVariable Long productId,
                               @RequestParam Integer stock) {
        return franchiseService.updateStock(productId, stock);
    }

    @DeleteMapping("/franchises/{franchiseId}/branches/{branchId}/products/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long franchiseId,
            @PathVariable Long branchId,
            @PathVariable Long productId) {

        franchiseService.deleteProduct(franchiseId, branchId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{franchiseId}/top-products")
    public List<TopProductDTO> getTopProducts(
            @PathVariable Long franchiseId
    ) {
        return franchiseService.getTopProducts(franchiseId);
    }
}