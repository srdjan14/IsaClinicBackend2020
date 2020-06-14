package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.CreateAdminRequest;
import com.ftn.isa.dto.request.CreateUserRequest;
import com.ftn.isa.dto.request.UpdateAdminRequest;
import com.ftn.isa.dto.response.AdminResponse;
import com.ftn.isa.dto.response.UserResponse;
import com.ftn.isa.entity.Admin;
import com.ftn.isa.entity.Clinic;
import com.ftn.isa.entity.User;
import com.ftn.isa.repository.AdminRepository;
import com.ftn.isa.repository.ClinicRepository;
import com.ftn.isa.repository.UserRepository;
import com.ftn.isa.service.IAdminService;
import com.ftn.isa.service.IUserService;
import com.ftn.isa.utils.enums.DeletedStatus;
import com.ftn.isa.utils.enums.UserType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository _adminRepository;

    private final IUserService _userService;

    private final UserRepository _userRepository;

    private final ClinicRepository _clinicRepository;

    public AdminService(AdminRepository adminRepository, IUserService userService, UserRepository userRepository, ClinicRepository clinicRepository) {
        _adminRepository = adminRepository;
        _userService = userService;
        _userRepository = userRepository;
        _clinicRepository = clinicRepository;
    }

    @Override
    public AdminResponse createAdmin(CreateAdminRequest genericRequest) throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword(genericRequest.getPassword());
        userRequest.setRePassword(genericRequest.getRePassword());
        userRequest.setAddress(genericRequest.getAddress());
        userRequest.setCity(genericRequest.getCity());
        userRequest.setEmail(genericRequest.getEmail());
        userRequest.setCountry(genericRequest.getCountry());
        userRequest.setFirstName(genericRequest.getFirstName());
        userRequest.setLastName(genericRequest.getLastName());
        userRequest.setSsn(genericRequest.getSsn());
        userRequest.setPhone(genericRequest.getPhone());
        userRequest.setUserType(UserType.ADMIN);
        // Save user to database and gets NOT User Entity, we get UserResponse
        UserResponse userResponse = _userService.createUser(userRequest);
        // Transform to user entity
        User user = _userRepository.findOneById(userResponse.getId());
        user.setId(userResponse.getId());

        Admin admin = new Admin();
        admin.setUser(user);
        Clinic clinic = _clinicRepository.findOneById(genericRequest.getClinicId());

        if(clinic == null) {
            throw new Exception("This clinic does not exist");
        }

        admin.setClinic(clinic);
        admin.setAdminType(genericRequest.getAdminType());

        Admin savedAdmin = _adminRepository.save(admin);

        return mapAdminToAdminResponse(savedAdmin);
    }

    @Override
    public AdminResponse getAdmin(Long id) throws Exception {
        Admin admin = _adminRepository.findOneById(id);

        if (admin == null) {
            throw new Exception(String.format("Admin with %s id is not found", id));
        }

        return mapAdminToAdminResponse(admin);
    }

    @Override
    public AdminResponse updateAdmin(Long id, UpdateAdminRequest request) throws Exception {
        Admin admin = _adminRepository.findOneById(id);

        if (admin == null) {
            throw new Exception(String.format("Patient with %s id is not found", id));
        }

        User user = admin.getUser();
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setSsn(request.getSsn());

        Admin savedAdmin = _adminRepository.save(admin);

        return mapAdminToAdminResponse(savedAdmin);
    }

    @Override
    public List<AdminResponse> getAdmins() {
        List<Admin> admins = _adminRepository.findAll();

        return admins
                .stream()
                .map(admin -> mapAdminToAdminResponse(admin))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAdmin(Long id) {
        Admin admin = _adminRepository.findOneById(id);
        admin.getUser().setDeletedStatus(DeletedStatus.IS_DELETED);
        _adminRepository.save(admin);
    }


    private AdminResponse mapAdminToAdminResponse(Admin admin) {
        AdminResponse adminResponse = new AdminResponse();
        User user = admin.getUser();
        adminResponse.setEmail(user.getEmail());
        adminResponse.setId(admin.getId());
        adminResponse.setAddress(user.getAddress());
        adminResponse.setCity(user.getCity());
        adminResponse.setCountry(user.getCountry());
        adminResponse.setFirstName(user.getFirstName());
        adminResponse.setLastName(user.getLastName());
        adminResponse.setPhone(user.getPhone());
        adminResponse.setSsn(user.getSsn());
        adminResponse.setAdminType(admin.getAdminType());

        return adminResponse;
    }
}
