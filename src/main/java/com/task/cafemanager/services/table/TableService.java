package com.task.cafemanager.services.table;

import com.task.cafemanager.data.entities.Table;
import com.task.cafemanager.services.table.model.TableModificationRequest;

import java.util.List;

public interface TableService {
    Table create(TableModificationRequest table);

    Table get(Long id);

    Table update(Long id, TableModificationRequest tableRequest);

    void delete(Long id);

    List<Table> getAll(Long id);

    void assignTable(Long id, Long userId);
}
