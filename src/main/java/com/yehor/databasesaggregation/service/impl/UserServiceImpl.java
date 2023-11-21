package com.yehor.databasesaggregation.service.impl;

import com.yehor.databasesaggregation.model.dto.UserDto;
import com.yehor.databasesaggregation.model.entity.User;
import com.yehor.databasesaggregation.model.request.RequestUser;
import com.yehor.databasesaggregation.service.UserService;
import com.yehor.databasesaggregation.service.impl.search.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserSpecification userSpecification;

    @Override
    public List<UserDto> getAll(RequestUser requestUser) {
        log.info("Searching users. Criterion are {}", requestUser);

        return userSpecification.searchUser(requestUser).stream()
                .map(this::userToUserDto)
                .toList();
    }

    private UserDto userToUserDto(User user) {
        return UserDto.builder().
                id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }
}
