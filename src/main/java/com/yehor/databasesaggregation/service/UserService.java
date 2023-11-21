package com.yehor.databasesaggregation.service;

import com.yehor.databasesaggregation.model.dto.UserDto;
import com.yehor.databasesaggregation.model.request.RequestUser;

import java.util.List;

public interface UserService {

    List<UserDto> getAll(RequestUser requestUser);

}
