package com.task.cafemanager.api.facade.dto;

import com.task.cafemanager.data.entities.enums.ProductInOrderStatus;
import lombok.Data;

@Data
public class ProductInOrderModificationRequestDto {

    private String name;
    private Integer amount;
    private ProductInOrderStatus status;

}
