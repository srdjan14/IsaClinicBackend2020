package com.ftn.isa.service;

import com.ftn.isa.dto.request.ChangePasswordRequest;
import com.ftn.isa.dto.request.FirstLoginPasswordRequest;
import com.ftn.isa.dto.request.LoginRequest;
import com.ftn.isa.dto.response.LoginResponse;

public interface IAuthService {

    LoginResponse login(LoginRequest request) throws Exception;

    LoginResponse changingDefaultPassword(Long id, FirstLoginPasswordRequest request) throws Exception;

    void changePassword(Long id, ChangePasswordRequest request) throws  Exception;
}
