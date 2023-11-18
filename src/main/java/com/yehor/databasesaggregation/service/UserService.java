package com.yehor.databasesaggregation.service;

import com.yehor.databasesaggregation.model.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

}
