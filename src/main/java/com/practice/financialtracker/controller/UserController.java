package com.practice.financialtracker.controller;

import com.practice.financialtracker.model.User;
import com.practice.financialtracker.model.UserDto;
import com.practice.financialtracker.service.UserService;
import com.practice.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseWrapper<UserDto>> getProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        UserDto user = userService.getUserById(userId);
        ResponseWrapper<UserDto> response = new ResponseWrapper<>();
        if (user != null) {
            response.setStatusCode(200);
            response.setMessage("User retrieved successfully");
            response.setSuccess(true);
            response.setResponse(user);
            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("User could not be found");
            response.setSuccess(false);
            return ResponseEntity.ok(response);
        }
    }


    @PutMapping()
    public ResponseEntity<ResponseWrapper<UserDto>> updateUser(@Valid @RequestBody User user, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        UserDto updatedUserDto = userService.updateUser(userId, user);
        ResponseWrapper<UserDto> response = new ResponseWrapper<>();
        try {
            if (updatedUserDto.getUserId() != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("User updated successfully");
                response.setSuccess(true);
                response.setResponse(updatedUserDto);
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

