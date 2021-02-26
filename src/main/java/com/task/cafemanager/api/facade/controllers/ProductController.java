package com.task.cafemanager.api.facade.controllers;

import com.task.cafemanager.services.product.ProductService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"Products"})
@RequestMapping(path = "/products")
@RestController
public class ProductController {

    private final MapperFacade mapper;
    private final ProductService productService;

    public ProductController(MapperFacade mapper,
                             ProductService productService) {
        this.mapper = mapper;
        this.productService = productService;
    }



}
