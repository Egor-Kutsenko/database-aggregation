package com.yehor.databasesaggregation.service.impl;

import com.yehor.databasesaggregation.config.database.DBContextHolder;
import com.yehor.databasesaggregation.model.dto.UserDto;
import com.yehor.databasesaggregation.model.entity.UserEntity;
import com.yehor.databasesaggregation.model.request.RequestUser;
import com.yehor.databasesaggregation.service.UserService;
import com.yehor.databasesaggregation.service.impl.search.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserSpecification userSpecification;
    private final List<String> databaseNames;

    @Override
    public List<UserDto> getAll(RequestUser requestUser) {
        log.info("Searching users. Criterion are {}", requestUser);

        return databaseNames.stream()
                .map(databaseName -> {
                    DBContextHolder.setCurrentDb(databaseName);
                    return userSpecification.searchUser(requestUser).stream()
                            .map(this::userToUserDto)
                            .toList();
                })
                .flatMap(Collection::stream)
                .toList();
    }

    private UserDto userToUserDto(UserEntity userEntity) {
        return UserDto.builder().
                id(userEntity.getId())
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .build();
    }
}
