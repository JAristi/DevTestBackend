package com.accenture.franchise_api.service.Impl;

import com.accenture.franchise_api.dto.TopProductDTO;
import com.accenture.franchise_api.entity.Branch;
import com.accenture.franchise_api.entity.Franchise;
import com.accenture.franchise_api.entity.Product;
import com.accenture.franchise_api.repository.BranchRepository;
import com.accenture.franchise_api.repository.FranchiseRepository;
import com.accenture.franchise_api.repository.ProductRepository;
import com.accenture.franchise_api.service.FranchiseService;
import com.accenture.franchise_api.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final RedisCacheService redisCacheService;

    @Override
    public Franchise createFranchise(String name) {

        Franchise franchise = Franchise.builder()
                .name(name)
                .build();

        return franchiseRepository.save(franchise);
    }

    @Override
    public Franchise updateFranchiseName(Long id, String name) {

        Franchise franchise = franchiseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Franchise not found"));

        franchise.setName(name);

        return franchiseRepository.save(franchise);
    }

    @Override
    public Branch addBranch(Long franchiseId, String name) {

        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow();

        Branch branch = Branch.builder()
                .name(name)
                .franchise(franchise)
                .build();

        return branchRepository.save(branch);
    }

    @Override
    public Branch updateBranchName(Long id, String name) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow();

        branch.setName(name);

        return branchRepository.save(branch);
    }

    @Override
    public Product addProduct(Long branchId, String name, Integer stock) {

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow();

        Product product = Product.builder()
                .name(name)
                .stock(stock)
                .branch(branch)
                .build();

        return productRepository.save(product);
    }

    @Override
    public Product updateProductName(Long id, String name) {

        Product product = productRepository.findById(id)
                .orElseThrow();

        product.setName(name);

        return productRepository.save(product);
    }

    @Override
    public Product updateStock(Long productId, Integer stock) {

        Product product = productRepository.findById(productId)
                .orElseThrow();

        product.setStock(stock);

        Product saved = productRepository.save(product);

        String cacheKey = "topProducts:" + product.getBranch()
                .getFranchise()
                .getId();

        redisCacheService.delete(cacheKey)
                .doOnNext(deleted -> {
                    if (deleted) {
                        log.debug("Caché invalidada correctamente: {}", cacheKey);
                    }
                })
                .subscribe();

        return saved;
    }

    @Override
    public void deleteProduct(Long franchiseId, Long branchId, Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getBranch().getId().equals(branchId) ||
                !product.getBranch().getFranchise().getId().equals(franchiseId)) {
            throw new RuntimeException("Product does not belong to the specified branch or franchise");
        }

        productRepository.delete(product);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TopProductDTO> getTopProducts(Long franchiseId) {

        // 🟢 COMENTADO TEMPORALMENTE - REDIS DESHABILITADO
        // String cacheKey = "topProducts:" + franchiseId;
        // List<TopProductDTO> cached = redisCacheService
        //         .get(cacheKey, List.class)
        //         .map(list -> (List<TopProductDTO>) list)
        //         .block();
        // if (cached != null) {
        //     return cached;
        // }

        List<Product> products = productRepository.findTopStockProducts(franchiseId);

        List<TopProductDTO> result = products
                .stream()
                .map(product -> new TopProductDTO(
                        product.getBranch().getId(),
                        product.getBranch().getName(),
                        product.getId(),
                        product.getName(),
                        product.getStock()
                ))
                .toList();

        // 🟢 COMENTADO TEMPORALMENTE - REDIS DESHABILITADO
        // redisCacheService.save(cacheKey, result).subscribe();

        return result;
    }
}
