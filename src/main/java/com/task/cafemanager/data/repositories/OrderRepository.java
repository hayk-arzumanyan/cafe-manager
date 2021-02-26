package com.task.cafemanager.data.repositories;

import com.task.cafemanager.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
