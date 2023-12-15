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

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<UserDto>> insertUser(@Valid @RequestBody User user) {
        ResponseWrapper<UserDto> response = new ResponseWrapper<>();
        try {
            UserDto newUser = userService.registerUser(user);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("User registered successfully");
            response.setResponse(newUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseWrapper<UserDto>> getUserById(@PathVariable("userId") long userId) {
        UserDto userDto = userService.getUserById(userId);
        ResponseWrapper<UserDto> response = new ResponseWrapper<>();
        try {
            if (userDto != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("User retrieved successfully");
                response.setSuccess(true);
                response.setResponse(userDto);
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<UserDto>>> getAllUser() {
        ResponseWrapper<List<UserDto>> response = new ResponseWrapper<>();
        try {
            List<UserDto> users = userService.getAllUser();
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Users retrieved successfully");
            response.setResponse(users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PutMapping("/{userId}")
    public ResponseEntity<ResponseWrapper<UserDto>> updateUser(HttpServletRequest request, @Valid @RequestBody User user) {
        Integer userId = (Integer) request.getAttribute("userId");
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

