package com.task.cafemanager.services.table.model;

import lombok.Data;

@Data
public class TableModificationRequest {

    private String tableName;
    private String waiterName;
    private Boolean isAssigned;

}
