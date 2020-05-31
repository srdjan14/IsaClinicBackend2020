package com.ftn.isa.controller;

import com.ftn.isa.dto.request.CreateAdminRequest;
import com.ftn.isa.dto.request.UpdateAdminRequest;
import com.ftn.isa.dto.response.AdminResponse;
import com.ftn.isa.service.IAdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final IAdminService _adminService;

    public AdminController(IAdminService adminService) {
        _adminService = adminService;
    }

    @PostMapping
    public AdminResponse createAdmin(@RequestBody CreateAdminRequest request) {
        try {
            return _adminService.createAdmin(request);
        } catch (Exception ex) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public AdminResponse updateAdmin(@PathVariable Long id, @RequestBody UpdateAdminRequest request) {
        try {
            return _adminService.updateAdmin(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}")
    public AdminResponse getAdmin(@PathVariable Long id) {
        try {
            return _adminService.getAdmin(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping
    public List<AdminResponse> getAdmins() {
        return _adminService.getAdmins();
    }

    @PutMapping("/delete/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        _adminService.deleteAdmin(id);
    }
}
