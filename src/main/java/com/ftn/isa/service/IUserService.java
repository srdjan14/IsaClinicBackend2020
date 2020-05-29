package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreateUserRequest;
import com.ftn.isa.dto.response.UserResponse;

public interface IUserService {

    UserResponse createUser(CreateUserRequest request) throws Exception;
}
