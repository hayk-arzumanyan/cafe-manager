package com.task.cafemanager.api.facade.dto;

import com.task.cafemanager.data.entities.enums.ProductInOrderStatus;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

@Data
public class ProductInOrderModificationRequestDto {

    private String name;
    private Integer amount;
    private ProductInOrderStatus status;
    private Long tableId;
    private Long orderId;
    private Long productId;

}
