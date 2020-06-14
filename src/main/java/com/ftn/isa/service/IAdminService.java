package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreateAdminRequest;
import com.ftn.isa.dto.request.UpdateAdminRequest;
import com.ftn.isa.dto.response.AdminResponse;

import java.util.List;

public interface IAdminService {

    AdminResponse createAdmin(CreateAdminRequest request) throws Exception;

    AdminResponse getAdmin(Long id) throws Exception;

    AdminResponse updateAdmin(Long id, UpdateAdminRequest request) throws Exception;

    List<AdminResponse> getAdmins();

    void deleteAdmin(Long id);
}
