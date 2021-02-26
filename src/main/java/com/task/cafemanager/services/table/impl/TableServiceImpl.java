package com.task.cafemanager.services.table.impl;

import com.task.cafemanager.data.entities.Table;
import com.task.cafemanager.data.entities.User;
import com.task.cafemanager.data.repositories.TableRepository;
import com.task.cafemanager.exceptions.ResourceNotFoundException;
import com.task.cafemanager.services.table.TableService;
import com.task.cafemanager.services.table.model.TableModificationRequest;
import com.task.cafemanager.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final UserService userService;

    public TableServiceImpl(TableRepository tableRepository, UserService userService) {
        this.tableRepository = tableRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Table create(TableModificationRequest tableRequest) {
        log.info("Creating table by name: {}", tableRequest.getTableName());
        Table table = new Table();
        table.setTableName(tableRequest.getTableName());
        table.setWaiterName(tableRequest.getWaiterName());
        table.setIsAssigned(tableRequest.getIsAssigned());
        return tableRepository.save(table);
    }

    @Transactional(readOnly = true)
    @Override
    public Table get(Long id) {
        log.info("Getting table by id: {}", id);
        return tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found."));
    }

    @Transactional
    @Override
    public Table update(Long id, TableModificationRequest tableRequest) {
        final Table table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find the table."));
        table.setIsAssigned(tableRequest.getIsAssigned());
        table.setTableName(tableRequest.getTableName());
        table.setWaiterName(tableRequest.getWaiterName());
        return tableRepository.save(table);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Deleting table by id {}", id);
        final Table table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find the table."));
        tableRepository.delete(table);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Table> getAll(Long id) {
        log.info("Getting all tables for waiter: {}", id);
        return tableRepository.findAllByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find the waiter."));
    }

    @Override
    public Table assignTable(Long id, Long userId) {
        User user = userService.get(userId);
        Table table = get(id);
//        if ((table.getUser().getId()) != null) {
//            log.info("Table was already assigned. Now was changed to User {}.", userId);
//        }
        table.setIsAssigned(true);
        table.setUser(user);
        tableRepository.save(table);
        return table;
    }
}
