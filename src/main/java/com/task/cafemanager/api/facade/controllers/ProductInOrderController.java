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
@RequestMapping(path = "/productsInOrder")
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
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrderDto> create(
            @RequestBody ProductInOrderModificationRequestDto request) {
        final Long orderId = request.getOrderId();
        final Long productId = request.getProductId();
        log.debug("Creating Product {} to Order {} with given request {}", productId, orderId,
                request);
        final ProductInOrderModificationRequest createdProductInOrder = mapper.map(request,
                ProductInOrderModificationRequest.class);
        final ProductInOrder result =
                productInOrderService.create(createdProductInOrder);
        final ProductInOrderDto resultDto = mapper.map(result, ProductInOrderDto.class);

        log.debug("Done creating Product {} to Order {} with given request {}", productId, orderId,
                request);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves ProductInOrder by id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrderDto> get(@PathVariable("id") Long id) {
        log.debug("Getting product in order by given id {}", id);
        final ProductInOrder dbProductInOrder = productInOrderService.get(id);
        final ProductInOrderDto result = mapper.map(dbProductInOrder, ProductInOrderDto.class);

        log.debug("Done getting product in order by given id {}", id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Updates ProductInOrder by id.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrderDto> update(@PathVariable("id") Long id,
                                                    @RequestBody ProductInOrderModificationRequestDto request) {
        log.debug("Updating ProductInOrder {} by given data: {}", id, request);
        final ProductInOrderModificationRequest updateProductInOrder = mapper.map(request,
                ProductInOrderModificationRequest.class);
        final ProductInOrder productInOrder =
                productInOrderService.update(id, updateProductInOrder);
        final ProductInOrderDto result = mapper.map(productInOrder, ProductInOrderDto.class);

        log.debug("Done updating ProductInOrder {} by given data: {}", id, request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete ProductInOrder by provided id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public void delete(@PathVariable("id") Long id) {
        log.debug("Deleting product in order by provided id: {}", id);
        productInOrderService.delete(id);
        log.debug("Done deleting product in order by provided id: {}", id);
    }
}
