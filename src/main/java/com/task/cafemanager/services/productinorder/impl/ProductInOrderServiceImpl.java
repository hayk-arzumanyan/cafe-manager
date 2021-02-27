package com.task.cafemanager.services.productinorder.impl;

import com.task.cafemanager.data.entities.Order;
import com.task.cafemanager.data.entities.Product;
import com.task.cafemanager.data.entities.ProductInOrder;
import com.task.cafemanager.data.repositories.ProductInOrderRepository;
import com.task.cafemanager.exceptions.ResourceNotFoundException;
import com.task.cafemanager.services.order.OrderService;
import com.task.cafemanager.services.product.ProductService;
import com.task.cafemanager.services.productinorder.ProductInOrderService;
import com.task.cafemanager.services.productinorder.model.ProductInOrderModificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductInOrderServiceImpl implements ProductInOrderService {

    private final ProductInOrderRepository productInOrderRepository;
    private final ProductService productService;
    private final OrderService orderService;

    public ProductInOrderServiceImpl(
            ProductInOrderRepository productInOrderRepository,
            ProductService productService,
            OrderService orderService) {
        this.productInOrderRepository = productInOrderRepository;
        this.productService = productService;
        this.orderService = orderService;
    }

    @Transactional
    @Override
    public ProductInOrder create(ProductInOrderModificationRequest modificationRequest) {
        final Long tableId = modificationRequest.getTableId();
        final Long orderId = modificationRequest.getOrderId();
        final Long productId = modificationRequest.getProductId();
        log.info("Creating ProductInOrder by Order id {} and Product id {}", orderId, productId);
        final Order order = orderService.get(tableId, orderId);
        final Product product = productService.get(productId);
        ProductInOrder productInOrder = new ProductInOrder();
        productInOrder.setName(modificationRequest.getName());
        productInOrder.setAmount(modificationRequest.getAmount());
        productInOrder.setStatus(modificationRequest.getStatus());
        productInOrder.setOrder(order);
        productInOrder.setProduct(product);
        return productInOrderRepository.save(productInOrder);
    }

    @Transactional
    @Override
    public ProductInOrder update(Long id, ProductInOrderModificationRequest modificationRequest) {
        final Long tableId = modificationRequest.getTableId();
        final Long orderId = modificationRequest.getOrderId();
        final Long productId = modificationRequest.getProductId();
        log.info("Getting productInOrder by id: {}", id);
        final Order order = orderService.get(tableId, orderId);
        final Product product = productService.get(productId);
        ProductInOrder productInOrder = get(id);
        productInOrder.setName(modificationRequest.getName());
        productInOrder.setAmount(modificationRequest.getAmount());
        productInOrder.setStatus(modificationRequest.getStatus());
        productInOrder.setOrder(order);
        productInOrder.setProduct(product);
        return productInOrderRepository.save(productInOrder);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductInOrder get(Long id) {
        log.info("Getting product in order by id: {}", id);
        return productInOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find product in order."));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Deleting product in order by id: {}", id);
        final ProductInOrder result = get(id);
        productInOrderRepository.delete(result);
    }
}
