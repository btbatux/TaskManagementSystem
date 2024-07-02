package com.btbatux.taskmanagement.controller;

import com.btbatux.taskmanagement.dto.TaskResponseDto;
import com.btbatux.taskmanagement.dto.UserRequestDto;
import com.btbatux.taskmanagement.dto.UserResponseDto;
import com.btbatux.taskmanagement.model.User;
import com.btbatux.taskmanagement.service.TaskService;
import com.btbatux.taskmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;


    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUser() {
        List<UserResponseDto> userResponseDtos = userService.getAllUser();
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
    }


    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createuser(userRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);

    }


}
