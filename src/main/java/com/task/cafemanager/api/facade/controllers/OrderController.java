package com.task.cafemanager.api.facade.controllers;


import com.task.cafemanager.api.facade.dto.OrderDto;
import com.task.cafemanager.api.facade.dto.OrderModificationRequestDto;
import com.task.cafemanager.data.entities.Order;
import com.task.cafemanager.services.order.OrderService;
import com.task.cafemanager.services.order.model.OrderModificationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"Orders"})
@RequestMapping(path = "/tables/orders")
@RestController
public class OrderController {

    private final MapperFacade mapper;

    private final OrderService orderService;

    public OrderController(MapperFacade mapper, OrderService orderService) {
        this.mapper = mapper;
        this.orderService = orderService;
    }

    @ApiOperation(value = "Create table order by provided request.")
    @PostMapping("/{tableId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<OrderDto> create(@PathVariable Long tableId,
                                           @RequestBody OrderModificationRequestDto request) {
        log.debug("Creating order for table {} by request: {}", tableId, request);
        final OrderModificationRequest orderRequest = mapper.map(request, OrderModificationRequest.class);
        final Order createdOrder = orderService.create(tableId, orderRequest);
        OrderDto result = mapper.map(createdOrder, OrderDto.class);
        log.info("Creating done.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Edit order by provided request.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<OrderDto> update(@PathVariable Long id, @RequestBody OrderModificationRequestDto request) {
        log.debug("Updating table order by id: {}", id);
        final OrderModificationRequest updateOrder = mapper.map(request, OrderModificationRequest.class);
        final Order dbOrder = orderService.update(id, updateOrder);
        final OrderDto result = mapper.map(dbOrder, OrderDto.class);
        log.info("Updating done.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get order by provided id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<OrderDto> get(@PathVariable Long id) {
        log.debug("Getting order by id: {}", id);
        final Order order = orderService.get(id);
        final OrderDto result = mapper.map(order, OrderDto.class);
        log.info("Getting done.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete order by provided id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public void delete(@PathVariable Long id) {
        log.debug("Deleting order by id {}", id);
        orderService.delete(id);
        log.info("Deleting done.");
    }

    @ApiOperation(value = "Assign Order to Table.")
    @PutMapping("{id}/assign/{tableId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public void assignOrder(@PathVariable("id") Long id, @PathVariable("tableId") Long tableId) {
        log.debug("Assigning order by id {} to table by id {}.", id, tableId);
        orderService.assignOrder(id, tableId);
    }

    @ApiOperation(value = "Get all orders for corresponding table.")
    @GetMapping("/getAllOrders/{tableId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<List<OrderDto>> getAllOrders(@PathVariable("tableId") Long tableId) {
        log.debug("Getting all orders for table: {}", tableId);
        final List<Order> orders = orderService.getAll(tableId);
        final List<OrderDto> result = mapper.mapAsList(orders, OrderDto.class);
        log.info("Getting all orders for corresponding table done.");
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


}
