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


    @Transactional
    @Override
    public Order create(Long id, OrderModificationRequest orderRequest) {
        log.info("Creating order for table by id: {}", id);
        final Table table = tableService.get(id);
        final boolean isOrderStatusStillOpen =
                table.getOrders().stream()
                        .anyMatch(order -> order.getOrderStatus().equals(OrderStatus.OPEN));
        if (isOrderStatusStillOpen) {
            throw new OrderStatusStillOpenException(
                    "Couldn't sign order for table: Table has Order with status OPENED");
        }
        final Order order = new Order();
        order.setOrderName(orderRequest.getOrderName());
        order.setOrderStatus(orderRequest.getOrderStatus());
        order.setTable(table);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Order get(Long id) {
        log.info("Getting order by its id: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find order."));
    }

    @Transactional
    @Override
    public Order update(Long id, OrderModificationRequest request) {
        log.info("Updating order data.");
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Cannot find order."));
        order.setOrderStatus(request.getOrderStatus());
        order.setOrderName(request.getOrderName());
        orderRepository.save(order);
        return order;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Deleting order: {}", id);
        final Order order = get(id);
        orderRepository.delete(order);
    }

    @Transactional
    @Override
    public Order assignOrder(Long id, Long tableId) {
        log.info("Assigning Order {} for Table {}.", id, tableId);
        Table table = tableService.get(tableId);
        Order order = get(id);
        final boolean isOrderStatusStillOpen =
                table.getOrders().stream()
                        .anyMatch(it -> it.getOrderStatus().equals(OrderStatus.OPEN));
        if (isOrderStatusStillOpen) {
            throw new OrderStatusStillOpenException(
                    "Couldn't sign order for table: Order status still OPENED");
        }
        order.setTable(table);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getAll(Long tableId) {
        log.info("Getting all orders for table: {}", tableId);
        return orderRepository.findAllByTableId(tableId).orElseThrow(
                () -> new ResourceNotFoundException("Couldn't find the corresponding table"));
    }
}
