package com.task.cafemanager.data.repositories;

import com.task.cafemanager.data.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
