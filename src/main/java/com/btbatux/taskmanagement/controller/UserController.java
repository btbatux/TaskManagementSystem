package com.btbatux.taskmanagement.controller;
import com.btbatux.taskmanagement.dto.UserRequestDto;
import com.btbatux.taskmanagement.dto.UserResponseDto;
import com.btbatux.taskmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto =  userService.createuser(userRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);

        }
}
