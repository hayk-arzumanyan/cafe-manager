package com.task.cafemanager.services.order;

import com.task.cafemanager.data.entities.Order;
import com.task.cafemanager.services.order.model.OrderModificationRequest;

import java.util.List;

public interface OrderService {
    Order get(Long tableId, Long id);

    List<Order> getAll(Long tableId);

    Order create(Long id, OrderModificationRequest orderModificationRequest);

    Order update(Long tableId, Long id, OrderModificationRequest request);

    void delete(Long tableId, Long id);
}
