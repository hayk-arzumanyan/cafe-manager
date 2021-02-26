package com.task.cafemanager.api.facade.controllers;

import com.task.cafemanager.api.facade.dto.TableDto;
import com.task.cafemanager.api.facade.dto.TableModificationRequestDto;
import com.task.cafemanager.api.facade.utils.AuthenticationUtils;
import com.task.cafemanager.data.entities.Table;
import com.task.cafemanager.services.table.TableService;
import com.task.cafemanager.services.table.model.TableModificationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"Cafe Tables"})
@RequestMapping(path = "/tables")
@RestController
public class TableController {

    private final MapperFacade mapper;
    private final TableService tableService;

    public TableController(MapperFacade mapper, TableService tableService) {
        this.mapper = mapper;
        this.tableService = tableService;
    }


    @ApiOperation(value = "Create Cafe Table by provided request.")
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<TableDto> create(@RequestBody TableModificationRequestDto request) {
        log.debug("Creating Table with given request: {}", request);
        final TableModificationRequest tableRequest =
                mapper.map(request, TableModificationRequest.class);
        final Table createdTable = tableService.create(tableRequest);
        final TableDto result = mapper.map(createdTable, TableDto.class);
        log.info("Table creation done.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves Table by id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<TableDto> get(@PathVariable("id") Long id) {
        log.debug("Getting user by given id {}", id);
        Table dbTable = tableService.get(id);
        TableDto result = mapper.map(dbTable, TableDto.class);
        log.info("Getting table done.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Updates Table by id.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<TableDto> update(@PathVariable Long id,
                                           @RequestBody TableModificationRequestDto request) {
        log.debug("Updating Table {} by given data: {}", id, request);
        final TableModificationRequest updateTable =
                mapper.map(request, TableModificationRequest.class);
        final Table table = tableService.update(id, updateTable);
        final TableDto result = mapper.map(table, TableDto.class);
        log.info("Updating table done.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Assign Table to User.")
    @PutMapping("/{id}/assign/users/{userId}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public void assignUser(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        log.debug("Assigning table {} to User {}", id, userId);
        tableService.assignTable(id, userId);

    }


    @ApiOperation(value = "Delete Table by provided id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public void delete(@PathVariable Long id) {
        log.debug("Deleting table by provided id: {}", id);
        tableService.delete(id);
        log.info("Deleting table done.");
    }

    @ApiOperation(value = "Get all tables for current waiter.")
    @GetMapping("/getAllTables")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<List<TableDto>> getAllTables() {
        final Long id = AuthenticationUtils.getUserId();
        log.debug("Getting all tables for: {}", id);
        final List<Table> tables = tableService.getAll(id);
        final List<TableDto> result = mapper.mapAsList(tables, TableDto.class);
        log.info("Getting all tables done.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
