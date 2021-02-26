package com.task.cafemanager.api.facade.dto;

import com.task.cafemanager.data.entities.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderDto {

    private Long id;
    private String orderName;
    private OrderStatus orderStatus;
}
