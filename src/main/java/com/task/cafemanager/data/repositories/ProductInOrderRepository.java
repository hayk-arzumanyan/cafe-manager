package com.task.cafemanager.data.repositories;

import com.task.cafemanager.data.entities.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {
}
