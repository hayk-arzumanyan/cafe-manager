package com.task.cafemanager.api.facade.dto;

import com.task.cafemanager.data.entities.enums.ProductInOrderStatus;
import lombok.Data;

@Data
public class ProductInOrderDto {

    private Long id;
    private String name;
    private Integer amount;
    private ProductInOrderStatus status;
    private OrderDto order;
    private ProductDto product;
}
