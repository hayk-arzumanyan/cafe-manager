package com.task.cafemanager.services.order.impl;

import com.task.cafemanager.data.entities.Order;
import com.task.cafemanager.data.entities.Table;
import com.task.cafemanager.data.entities.enums.OrderStatus;
import com.task.cafemanager.data.repositories.OrderRepository;
import com.task.cafemanager.exceptions.OrderStatusStillOpenException;
import com.task.cafemanager.exceptions.ResourceNotFoundException;
import com.task.cafemanager.services.order.OrderService;
import com.task.cafemanager.services.order.model.OrderModificationRequest;
import com.task.cafemanager.services.table.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final TableService tableService;

    public OrderServiceImpl(OrderRepository orderRepository, TableService tableService) {
        this.orderRepository = orderRepository;
        this.tableService = tableService;
    }

    @Transactional(readOnly = true)
    @Override
    public Order get(Long tableId, Long id) {
        log.info("Getting order by id: {}", id);
        return orderRepository.findByIdAndTableId(tableId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find order."));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getAll(Long tableId) {
        log.info("Getting all orders for table: {}", tableId);
        return orderRepository.findAllByTableId(tableId).orElseThrow(
                () -> new ResourceNotFoundException("Couldn't find the corresponding table"));
    }

    @Transactional
    @Override
    public Order create(Long tableId, OrderModificationRequest orderRequest) {
        log.info("Creating order for table by tableId: {}", tableId);
        final Table table = tableService.get(tableId);
        checkIfContainsOpenOrder(table);
        final Order order = new Order();
        order.setOrderName(orderRequest.getOrderName());
        order.setOrderStatus(orderRequest.getOrderStatus());
        order.setTable(table);
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order update(Long tableId, Long id, OrderModificationRequest request) {
        log.info("Updating order data.");
        final Table table = tableService.get(tableId);
        if(request.getOrderStatus().equals(OrderStatus.OPEN)) {
            checkIfContainsOpenOrder(table);
        }
        Order order = get(tableId, id);
        order.setOrderStatus(request.getOrderStatus());
        order.setOrderName(request.getOrderName());
        order.setTable(table);
        orderRepository.save(order);
        return order;
    }

    @Transactional
    @Override
    public void delete(Long tableId, Long id) {
        log.info("Deleting order: {}", id);
        final Order order = get(tableId, id);
        orderRepository.delete(order);
    }

    private static void checkIfContainsOpenOrder(Table table) {
        final boolean isOrderStatusStillOpen =
                table.getOrders().stream()
                        .anyMatch(order -> order.getOrderStatus().equals(OrderStatus.OPEN));
        if (isOrderStatusStillOpen) {
            throw new OrderStatusStillOpenException("Table contains order with status OPEN");
        }
    }
}
