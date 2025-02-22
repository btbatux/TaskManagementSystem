package com.btbatux.taskmanagement.service;

import com.btbatux.taskmanagement.dto.UserRequestDto;
import com.btbatux.taskmanagement.dto.UserResponseDto;
import com.btbatux.taskmanagement.exception.ResourceNotFoundException;
import com.btbatux.taskmanagement.model.User;
import com.btbatux.taskmanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    public final UserRepository userRepository;
    public final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public List<UserResponseDto> getAllUser() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw  new ResourceNotFoundException("User Bulunamadı");
        }
       return userList.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).collect(Collectors.toList());

    }

    //User create
    public UserResponseDto createuser(UserRequestDto userRequestDto) {
        User user = modelMapper.map(userRequestDto, User.class);
        user = userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }


}
