package com.example.jwtdemo.controller;

import com.example.jwtdemo.payloads.ApiResponse;
import com.example.jwtdemo.payloads.UserDto;
import com.example.jwtdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable("userId") Integer user_id){
        return userService.getSpecificUser(user_id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer user_id){
        userService.deleteSpecificUser(user_id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true), HttpStatus.OK);
    }

    @GetMapping("/all-users")
    public List<UserDto> getAllUser(){
        return userService.findAllUsers();
    }
}
