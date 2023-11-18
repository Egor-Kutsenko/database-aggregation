package com.yehor.databasesaggregation.service.impl;

import com.yehor.databasesaggregation.model.dto.UserDto;
import com.yehor.databasesaggregation.model.entity.User;
import com.yehor.databasesaggregation.repository.UserRepository;
import com.yehor.databasesaggregation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::userToUserDto)
                .toList();
    }

    private UserDto userToUserDto (User user){
        return UserDto.builder().
                id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }
}
