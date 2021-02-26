package com.task.cafemanager.services.product;

import com.task.cafemanager.data.entities.Product;
import com.task.cafemanager.services.product.model.ProductModificationRequest;

public interface ProductService {
    Product create(ProductModificationRequest modificationRequest);

    Product update(Long id, ProductModificationRequest modificationRequest);

    Product get(Long id);

    void delete(Long id);
}
