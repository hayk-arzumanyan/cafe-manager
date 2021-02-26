package com.task.cafemanager.data.repositories;

import com.task.cafemanager.data.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<Table, Long> {
    Optional<Table> findByWaiterName(String name);

    Optional<List<Table>> findAllByUserId(Long id);
}