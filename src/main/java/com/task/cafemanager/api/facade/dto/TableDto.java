package com.task.cafemanager.api.facade.dto;

import lombok.Data;

import java.util.Set;

@Data
public class TableDto {

    private Long id;
    private String tableName;
    private String waiterName;
    private Boolean isAssigned;
    private Set<OrderDto> orders;
}
