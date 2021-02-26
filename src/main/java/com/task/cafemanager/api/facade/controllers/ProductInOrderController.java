package com.task.cafemanager.api.facade.controllers;

import com.task.cafemanager.api.facade.dto.ProductInOrderDto;
import com.task.cafemanager.api.facade.dto.ProductInOrderModificationRequestDto;
import com.task.cafemanager.data.entities.ProductInOrder;
import com.task.cafemanager.services.productinorder.ProductInOrderService;
import com.task.cafemanager.services.productinorder.model.ProductInOrderModificationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "Create and sign ProductInOrder by provided request.")
    @PostMapping("/assign/{orderId}/{productId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrderDto> create(@PathVariable("orderId") Long orderId,
                                                    @PathVariable(
                                                            "productId") Long productId,
                                                    @RequestBody ProductInOrderModificationRequestDto request) {
        log.debug("Creating ProductInOrder assigned to Order by id {} and to Product by id {} " +
                "with given request: {}", orderId, productId, request);
        final ProductInOrderModificationRequest createdProductInOrder = mapper.map(request,
                ProductInOrderModificationRequest.class);
        final ProductInOrder result =
                productInOrderService.create(orderId, productId, createdProductInOrder);
        final ProductInOrderDto resultDto = mapper.map(result, ProductInOrderDto.class);
        log.info("Done creating product in order.");
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Product by id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrderDto> get(@PathVariable("id") Long id) {
        log.debug("Getting product in order by given id {}", id);
        final ProductInOrder dbProductInOrder = productInOrderService.get(id);
        final ProductInOrderDto result = mapper.map(dbProductInOrder, ProductInOrderDto.class);
        log.info("Getting product in order done.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Updates ProductInOrder by id.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrderDto> update(@PathVariable Long id,
                                             @RequestBody ProductInOrderModificationRequestDto request) {
        log.debug("Updating ProductInOrder {} by given data: {}", id, request);
        final ProductInOrderModificationRequest updateProductInOrder = mapper.map(request,
                ProductInOrderModificationRequest.class);
        final ProductInOrder productInOrder = productInOrderService.update(id, updateProductInOrder);
        final ProductInOrderDto result = mapper.map(productInOrder, ProductInOrderDto.class);
        log.info("Updating product done.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete ProductInOrder by provided id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public void delete(@PathVariable Long id) {
        log.debug("Deleting product in order by provided id: {}", id);
        productInOrderService.delete(id);
        log.info("Deleting product in order done.");
    }

}
