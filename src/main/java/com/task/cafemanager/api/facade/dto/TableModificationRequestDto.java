package com.task.cafemanager.api.facade.dto;

import lombok.Data;

@Data
public class TableModificationRequestDto {

    private String tableName;
    private String waiterName;
    private Boolean isAssigned;

}
