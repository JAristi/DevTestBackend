package com.accenture.franchise_api.repository;

import com.accenture.franchise_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT p
        FROM Product p
        WHERE p.branch.franchise.id = :franchiseId
        AND p.stock = (
            SELECT MAX(p2.stock)
            FROM Product p2
            WHERE p2.branch.id = p.branch.id
        )
    """)
    List<Product> findTopStockProducts(Long franchiseId);
}
