package com.task.cafemanager.services.product.impl;

import com.task.cafemanager.data.entities.Product;
import com.task.cafemanager.data.repositories.ProductRepository;
import com.task.cafemanager.exceptions.ResourceNotFoundException;
import com.task.cafemanager.services.product.ProductService;
import com.task.cafemanager.services.product.model.ProductModificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Product get(Long id) {
        log.info("Getting product by id: {}", id);
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find product."));
    }

    @Transactional
    @Override
    public Product create(ProductModificationRequest modificationRequest) {
        log.info("Creating product by name {}", modificationRequest.getProductName());
        Product product = new Product();
        product.setProductName(modificationRequest.getProductName());
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Product update(Long id, ProductModificationRequest modificationRequest) {
        log.info("Getting product by id: {}", id);
        Product product = get(id);
        log.info("Updating product {}:", product.getProductName());
        product.setProductName(modificationRequest.getProductName());
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Deleting product by id: {}", id);
        final Product product = get(id);
        productRepository.delete(product);
    }
}
