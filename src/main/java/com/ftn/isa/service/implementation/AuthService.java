package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.ChangePasswordRequest;
import com.ftn.isa.dto.request.FirstLoginPasswordRequest;
import com.ftn.isa.dto.request.LoginRequest;
import com.ftn.isa.dto.response.ClinicResponse;
import com.ftn.isa.dto.response.LoginResponse;
import com.ftn.isa.dto.response.UserResponse;
import com.ftn.isa.entity.Admin;
import com.ftn.isa.entity.MedicalStaff;
import com.ftn.isa.entity.Patient;
import com.ftn.isa.entity.User;
import com.ftn.isa.repository.AdminRepository;
import com.ftn.isa.repository.MedicalStaffRepository;
import com.ftn.isa.repository.PatientRepository;
import com.ftn.isa.repository.UserRepository;
import com.ftn.isa.service.IClinicService;
import com.ftn.isa.service.IAuthService;
import com.ftn.isa.utils.enums.DeletedStatus;
import com.ftn.isa.utils.enums.UserType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements IAuthService {

    private final PasswordEncoder _passwordEncoder;

    private final UserRepository _userRepository;

    private final MedicalStaffRepository _medicalStaffRepository;

    private final IClinicService _clinicService;

    private final AdminRepository _adminRepository;


    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, MedicalStaffRepository medicalStaffRepository, IClinicService IClinicService, AdminRepository adminRepository) {
        _passwordEncoder = passwordEncoder;
        _userRepository = userRepository;
        _medicalStaffRepository = medicalStaffRepository;
        _clinicService = IClinicService;
        _adminRepository = adminRepository;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws Exception {
        User user = _userRepository.findOneByEmail(request.getEmail());

        if (user == null) {
            throw new Exception("This email doesn't exist!");
        }

        if (user.getDeletedStatus() == DeletedStatus.IS_DELETED) {
            throw new Exception("User has been deleted");
        }

        if (!_passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception("Wrong password!");
        }

        if (user.getUserType().equals(UserType.PATIENT)) {
            user.setFirstLogin(true);
            _userRepository.save(user);
        }

//        if (user.getUserType().equals(UserType.PATIENT) && !user.getPatient().getActive()) {
//            throw new Exception("Your account is not active");
//        }

        UserResponse userResponse = mapUserToUserResponse(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(userResponse);

        return loginResponse;
    }

    @Override
    public LoginResponse changingDefaultPassword(Long id, FirstLoginPasswordRequest request) throws Exception {
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new Exception("Password must match");
        }

        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);
        if(!(medicalStaff == null)) {
            User user = medicalStaff.getUser();

            user.setPassword(_passwordEncoder.encode(request.getPassword()));
            user.setFirstLogin(true);

            _medicalStaffRepository.save(medicalStaff);

            UserResponse userResponse = mapUserToUserResponse(user);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUser(userResponse);

            return loginResponse;
        } else {
            Admin admin = _adminRepository.findOneById(id);
            User user = admin.getUser();

            user.setPassword(_passwordEncoder.encode(request.getPassword()));
            user.setFirstLogin(true);

            _adminRepository.save(admin);

            UserResponse userResponse = mapUserToUserResponse(user);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUser(userResponse);

            return loginResponse;
        }
    }

    @Override
    public void changePassword(Long id, ChangePasswordRequest request) throws Exception {

        if(!request.getPassword().equals(request.getRePassword())) {
            throw new Exception("Password must match");
        }

        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);
        if(!(medicalStaff == null)) {
            User user = medicalStaff.getUser();
            user.setPassword(_passwordEncoder.encode(request.getPassword()));
            _medicalStaffRepository.save(medicalStaff);
        } else {
            Admin admin = _adminRepository.findOneById(id);
            User user = admin.getUser();
            user.setPassword(_passwordEncoder.encode(request.getPassword()));
            _adminRepository.save(admin);
        }

    }

    private UserResponse mapUserToUserResponse(User user) throws Exception {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        Long id = null;
        Long clinicId = null;
        if (user.getUserType().equals(UserType.PATIENT)) {
            id = user.getPatient().getId();
        } else if (user.getUserType().equals(UserType.MEDICAL)) {
            id = user.getMedicalStaff().getId();
            clinicId = user.getMedicalStaff().getClinic().getId();
        } else if (user.getUserType().equals(UserType.ADMIN)) {
            id = user.getAdmin().getId();
            clinicId = user.getAdmin().getClinic().getId();
            userResponse.setAdminType(user.getAdmin().getAdminType());
        }
        if (user.getUserType().equals(UserType.ADMIN) || user.getUserType().equals(UserType.MEDICAL)) {
            ClinicResponse clinicResponse = _clinicService.getClinic(clinicId);
            userResponse.setMyClinic(clinicResponse);
        }
        userResponse.setUserId(user.getId());
        userResponse.setId(id);
        userResponse.setAddress(user.getAddress());
        userResponse.setCity(user.getCity());
        userResponse.setCountry(user.getCountry());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhone(user.getPhone());
        userResponse.setSsn(user.getSsn());
        userResponse.setUserType(user.getUserType());
        userResponse.setDeletedStatus(DeletedStatus.NOT_DELETED);

        // only on login
        userResponse.setSetNewPassword(user.getFirstLogin() == false);

        return userResponse;
    }
}
