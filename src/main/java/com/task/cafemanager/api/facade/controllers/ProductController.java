package com.task.cafemanager.api.facade.controllers;

import com.task.cafemanager.api.facade.dto.ProductDto;
import com.task.cafemanager.api.facade.dto.ProductModificationRequestDto;
import com.task.cafemanager.data.entities.Product;
import com.task.cafemanager.services.product.ProductService;
import com.task.cafemanager.services.product.model.ProductModificationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "Create Product by provided request.")
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<ProductDto> create(@RequestBody ProductModificationRequestDto request) {
        log.debug("Creating Product with given request: {}", request);
        final ProductModificationRequest createdProduct = mapper.map(request,
                ProductModificationRequest.class);
        final Product result = productService.create(createdProduct);
        final ProductDto resultDto = mapper.map(result, ProductDto.class);
        log.info("Done creating Product with given request: {}", request);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Product by id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<ProductDto> get(@PathVariable("id") Long id) {
        log.debug("Getting product by given id {}", id);
        final Product dbProduct = productService.get(id);
        final ProductDto result = mapper.map(dbProduct, ProductDto.class);
        log.info("Done getting product by given id {}", id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Updates Product by id.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<ProductDto> update(@PathVariable("id") Long id,
                                             @RequestBody ProductModificationRequestDto request) {
        log.debug("Updating Product {} by given data: {}", id, request);
        final ProductModificationRequest updateProduct = mapper.map(request,
                ProductModificationRequest.class);
        final Product product = productService.update(id, updateProduct);
        final ProductDto result = mapper.map(product, ProductDto.class);
        log.info("Done updating Product {} by given data: {}", id, request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Product by provided id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public void delete(@PathVariable Long id) {
        log.debug("Deleting product by provided id: {}", id);
        productService.delete(id);
        log.info("Done deleting product by provided id: {}", id);
    }
}
