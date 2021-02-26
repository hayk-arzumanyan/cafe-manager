package com.task.cafemanager.services.order;

import com.task.cafemanager.data.entities.Order;
import com.task.cafemanager.services.order.model.OrderModificationRequest;

import java.util.List;

public interface OrderService {
    Order create(Long id, OrderModificationRequest orderModificationRequest);
    Order get(Long id);
    Order update(Long id, OrderModificationRequest request);
    void delete(Long id);
    Order assignOrder(Long id, Long tableId);
    List<Order> getAll(Long tableId);
}
