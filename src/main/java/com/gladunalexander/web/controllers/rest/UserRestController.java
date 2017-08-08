package com.gladunalexander.web.controllers.rest;

import com.gladunalexander.backend.persistence.domain.User;
import com.gladunalexander.backend.service.UserService;
import com.gladunalexander.exceptions.DuplicateUserException;
import com.gladunalexander.exceptions.UserIdMismatchException;
import com.gladunalexander.exceptions.UserNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Alexander Gladun
 * Rest Service for users management
 */
@RestController
@RequestMapping("/rest/users")
@Api(value="springstore", description="Operations for Users Management")
public class UserRestController {


    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation(value = "View a list of users", response = List.class)
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search a user with an ID",response = User.class)
    public User findById(@PathVariable long id){
        User user = userService.findById(id);
        if (user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a user")
    public User createUser(@RequestBody User user){
        if (userService.duplicatesExists(user)){
            throw new DuplicateUserException();
        }
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete a user")
    public void deleteUser(@PathVariable long id){
        User user = userService.findById(id);
        if (user == null){
            throw new UserNotFoundException();
        }
        userService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a usert")
    public User updateUser(@RequestBody User user,
                           @PathVariable long id){
        if (user.getId() != id){
            throw new UserIdMismatchException();
        }
        User old = userService.findById(id);
        if (old == null){
            throw new UserNotFoundException();
        }

        if (userService.duplicatesExists(user)){
            throw new DuplicateUserException();
        }
        return userService.saveOrUpdate(user);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a user status")
    public void updateUserStatus(@RequestBody boolean status,
                                 @PathVariable long id){
        if (userService.findById(id) == null){
            throw new UserNotFoundException();
        }
        userService.updateUserStatus(id, status);
    }
}
