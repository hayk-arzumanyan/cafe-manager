package com.task.cafemanager.services.productinorder;

import com.task.cafemanager.data.entities.ProductInOrder;
import com.task.cafemanager.services.productinorder.model.ProductInOrderModificationRequest;

public interface ProductInOrderService {
    ProductInOrder create(Long orderId,
                          Long productId, ProductInOrderModificationRequest modificationRequest);

    ProductInOrder update(Long id, ProductInOrderModificationRequest modificationRequest);

    ProductInOrder get(Long id);

    void delete(Long id);

    void assignToProduct(Long id, Long productId);

    void assignToOrder(Long id, Long orderId);
}
