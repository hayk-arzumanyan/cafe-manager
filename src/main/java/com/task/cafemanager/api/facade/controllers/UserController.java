package com.task.cafemanager.api.facade.controllers;

import com.task.cafemanager.api.facade.dto.UserDto;
import com.task.cafemanager.api.facade.dto.UserCreationRequestDto;
import com.task.cafemanager.api.facade.dto.UserUpdateRequestDto;
import com.task.cafemanager.api.facade.utils.AuthenticationUtils;
import com.task.cafemanager.data.entities.User;
import com.task.cafemanager.services.user.UserService;
import com.task.cafemanager.services.user.model.UserCreationRequest;
import com.task.cafemanager.services.user.model.UserUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = {"Users"})
@RequestMapping(path = "/users")
@RestController
public class UserController {

    private final MapperFacade mapper;
    private final UserService userService;

    public UserController(MapperFacade mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @ApiOperation(value = "Retrieves User itself.")
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<UserDto> get() {
        final Long id = AuthenticationUtils.getUserId();
        log.debug("Getting User by given id: {}", id);
        final User dbUser = userService.get(id);
        final UserDto result = mapper.map(dbUser, UserDto.class);
        log.info("Done getting user by id.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves User by id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<UserDto> get(@PathVariable("id") Long id) {
        log.debug("Getting User by given id: {}", id);
        final User result = userService.get(id);
        final UserDto resultDto = mapper.map(result, UserDto.class);
        log.info("Done getting user by id: {}.", id);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Create User by provided request.")
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<UserDto> create(@RequestBody UserCreationRequestDto requestDto) {
        log.debug("Creating User with given request: {}", requestDto);
        final UserCreationRequest request =
                mapper.map(requestDto, UserCreationRequest.class);
        final User result = userService.create(request);
        final UserDto resultDto = mapper.map(result, UserDto.class);
        log.info("Done creating User with given request: {}", requestDto);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Updates User by provided request.")
    @PutMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<UserDto> update(@RequestBody UserUpdateRequestDto userRequestDto) {
        final Long id = AuthenticationUtils.getUserId();
        log.debug("Updating User data by given id: {}", id);
        final UserUpdateRequest
                updateUser = mapper.map(userRequestDto, UserUpdateRequest.class);
        final User result = userService.update(id, updateUser);
        final UserDto resultDto = mapper.map(result, UserDto.class);
        log.info("Done updating User data by given id: {}", id);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete User itself.")
    @DeleteMapping("/me")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public void delete() {
        final Long id = AuthenticationUtils.getUserId();
        log.debug("Deleting user by given id: {}", id);
        userService.delete(id);
        log.debug("Done deleting user by given id: {}", id);
    }
}
