package com.task.cafemanager.api.facade.controllers;

import com.task.cafemanager.services.productinorder.ProductInOrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"Products In Order"})
@RequestMapping(path = "/tables/orders/productInOrder")
@RestController
public class ProductInOrderController {

    private final MapperFacade mapper;
    private final ProductInOrderService productInOrderService;

    public ProductInOrderController(MapperFacade mapper,
                                    ProductInOrderService productInOrderService) {
        this.mapper = mapper;
        this.productInOrderService = productInOrderService;
    }
}
