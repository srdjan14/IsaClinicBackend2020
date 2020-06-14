package com.ftn.isa.config;

import com.ftn.isa.dto.request.CreateUserRequest;
import com.ftn.isa.dto.response.UserResponse;
import com.ftn.isa.entity.Admin;
import com.ftn.isa.entity.MedicalStaff;
import com.ftn.isa.entity.Patient;
import com.ftn.isa.entity.User;
import com.ftn.isa.repository.AdminRepository;
import com.ftn.isa.repository.MedicalStaffRepository;
import com.ftn.isa.repository.PatientRepository;
import com.ftn.isa.service.IUserService;
import com.ftn.isa.utils.enums.DeletedStatus;
import com.ftn.isa.utils.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DataLoader implements ApplicationRunner {

    private List<Long> ids = new ArrayList();

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicalStaffRepository medicalStaffRepository;

    @Autowired
    private IUserService userService;

    private void setupIds() {
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);
        ids.add(5L);
        ids.add(6L);
        ids.add(7L);
        ids.add(8L);
        ids.add(9L);
        ids.add(10L);
        ids.add(11L);
        ids.add(12L);
        ids.add(13L);
        ids.add(14L);
        ids.add(15L);
        ids.add(16L);
        ids.add(17L);
        ids.add(18L);
        ids.add(19L);
        ids.add(20L);
        ids.add(21L);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        setupIds();
        for (int i = 0; i < 20; i++) {
            try {
                Admin admin = null;
                Patient patient = null;
                MedicalStaff medicalStaff = null;
                CreateUserRequest request = new CreateUserRequest();
                if (i <= 7) {
                    admin = adminRepository.findOneById(ids.get(i));
                    request.setUserType(UserType.ADMIN);
                } else if (i > 7 && i <= 13) {
                    patient = patientRepository.findOneById(ids.get(i));
                    request.setUserType(UserType.PATIENT);
                } else {
                    medicalStaff = medicalStaffRepository.findOneById(ids.get(i));
                    request.setUserType(UserType.MEDICAL);
                }
                request.setEmail(String.format("user%s@test.com", i));
                request.setSsn(String.format("123123132%s", i));
                request.setAddress(String.format("adresa %s", i));
                request.setCity(String.format("grad %s", i));
                request.setCountry(String.format("drzava %s", i));
                request.setFirstName(String.format("Ime %s", i));
                request.setLastName(String.format("Prezime %s", i));
                request.setPhone(String.format("telefon %s", i));
                request.setPassword(String.format("pass%s", i));
                request.setRePassword(String.format("pass%s", i));
                request.setDeletedStatus(DeletedStatus.NOT_DELETED);
                UserResponse userResponse = userService.createUser(request);
                User user = new User();
                user.setId(userResponse.getId());
                if (i <= 7) {
                    admin.setUser(user);
                    adminRepository.save(admin);
                }else if (i > 7 && i <= 13) {
                    patient.setUser(user);
                    patientRepository.save(patient);
                } else {
                    medicalStaff.setUser(user);
                    medicalStaffRepository.save(medicalStaff);
                }
            } catch (Exception ex) {
                // nothing
            }
        }
    }
}