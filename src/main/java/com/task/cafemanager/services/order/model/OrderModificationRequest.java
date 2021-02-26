package com.task.cafemanager.services.order.model;

import com.task.cafemanager.data.entities.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderModificationRequest {

    private String orderName;
    private OrderStatus orderStatus;

}
