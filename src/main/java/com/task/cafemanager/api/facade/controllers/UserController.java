package com.task.cafemanager.api.facade.controllers;

import com.task.cafemanager.api.facade.dto.UserDto;
import com.task.cafemanager.api.facade.dto.UserModificationRequestDto;
import com.task.cafemanager.api.facade.utils.AuthenticationUtils;
import com.task.cafemanager.data.entities.User;
import com.task.cafemanager.services.user.UserService;
import com.task.cafemanager.services.user.model.UserModificationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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

    @ApiOperation(value = "Create User by provided request.")
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<UserDto> create(@RequestBody UserModificationRequestDto request) {
        log.debug("Creating User with given request: {}", request);
        final UserModificationRequest createdUser = mapper.map(request, UserModificationRequest.class);
        final User result = userService.create(createdUser);
        final UserDto resultDto = mapper.map(result, UserDto.class);
        log.info("Done creating User.");
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves User by username.")
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<UserDto> get(@RequestParam("username") String username) {
        log.debug("Getting User by given username: {}", username);
        final User dbUser = userService.getByUsername(username);
        final UserDto result = mapper.map(dbUser, UserDto.class);
        log.info("Done getting user by username.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves User by id.")
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<UserDto> get() {
        final Long id = AuthenticationUtils.getUserId();
        log.debug("Getting User by given id: {}", id);
        final User dbUser = userService.get(id);
        final UserDto result = mapper.map(dbUser, UserDto.class);
        log.info("Done getting user by id.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete User by provided id.")
    @DeleteMapping("/me")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public void delete() {
        final Long id = AuthenticationUtils.getUserId();
        log.debug("Deleting user by given id: {}", id);
        userService.delete(id);
        log.info("Deleting done.");
    }

//    @ApiOperation(value = "Delete User by provided username.")
//    @DeleteMapping("/{username}")
//    @PreAuthorize("hasAnyRole('MANAGER')")
//    public void delete(@PathVariable("username") String username) {
//        log.info("Deleting user by given username: {}", username);
//        userService.delete(username);
//    }

    @ApiOperation(value = "Updates User by provided request.")
    @PutMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<UserDto> update(@RequestBody UserModificationRequestDto userRequestDto) {
        final Long id = AuthenticationUtils.getUserId();
        log.debug("Updating User data by given id: {}", id);
        final UserModificationRequest
                updateUser = mapper.map(userRequestDto, UserModificationRequest.class);
        final User result = userService.update(id, updateUser);
        final UserDto resultDto = mapper.map(result, UserDto.class);
        log.info("Done updating User data by given id: {}", id);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

}
