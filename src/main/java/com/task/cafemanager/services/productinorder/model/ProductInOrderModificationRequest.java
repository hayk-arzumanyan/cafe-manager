package com.task.cafemanager.services.productinorder.model;

import com.task.cafemanager.data.entities.enums.ProductInOrderStatus;
import lombok.Data;

@Data
public class ProductInOrderModificationRequest {
    private final String name;
    private final Integer amount;
    private final ProductInOrderStatus status;
}
