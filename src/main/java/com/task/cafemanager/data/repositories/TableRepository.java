package com.task.cafemanager.data.repositories;

import com.task.cafemanager.data.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TableRepository extends JpaRepository<Table, Long> {
}
