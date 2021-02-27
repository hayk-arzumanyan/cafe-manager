package com.task.cafemanager.data.repositories;

import com.task.cafemanager.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndTableId(Long id, Long tableId);
    Optional<List<Order>> findAllByTableId(Long id);
}