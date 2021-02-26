package com.task.cafemanager.services.product.impl;

import com.task.cafemanager.data.entities.Product;
import com.task.cafemanager.services.product.ProductService;
import com.task.cafemanager.services.product.model.ProductModificationRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product create(ProductModificationRequest modificationRequest) {
        return null;
    }

    @Override
    public Product update(Long id, ProductModificationRequest modificationRequest) {
        return null;
    }

    @Override
    public Product get(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
