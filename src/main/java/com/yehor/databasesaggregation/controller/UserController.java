package com.yehor.databasesaggregation.controller;

import com.yehor.databasesaggregation.model.dto.UserDto;
import com.yehor.databasesaggregation.model.request.RequestUser;
import com.yehor.databasesaggregation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("database-aggregation/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findUsers(RequestUser requestUser) {
        return userService.getAll(requestUser);
    }
}
