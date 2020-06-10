package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.CreateMedicalStaffRequest;
import com.ftn.isa.dto.request.CreateUserRequest;
import com.ftn.isa.dto.request.SearchMedicalStaffRequest;
import com.ftn.isa.dto.request.UpdateMedicalStaffRequest;
import com.ftn.isa.dto.response.MedicalStaffResponse;
import com.ftn.isa.dto.response.UserResponse;
import com.ftn.isa.entity.*;
import com.ftn.isa.repository.ClinicRepository;
import com.ftn.isa.repository.ExaminationTypeRepository;
import com.ftn.isa.repository.MedicalStaffRepository;
import com.ftn.isa.repository.UserRepository;
import com.ftn.isa.service.IMedicalStaffService;
import com.ftn.isa.service.IUserService;
import com.ftn.isa.utils.enums.DeletedStatus;
import com.ftn.isa.utils.enums.UserType;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalStaffService implements IMedicalStaffService {

    @PersistenceContext
    private EntityManager entityManager;

    private final QClinic qClinic = QClinic.clinic;

    private final QMedicalStaff qMedicalStaff = QMedicalStaff.medicalStaff;

    private final MedicalStaffRepository _medicalStaffRepository;

    private final IUserService _userService;

    private final UserRepository _userRepository;

    private final ClinicRepository _clinicRepository;

    private final ExaminationTypeRepository _examinationTypeRepository;

    public MedicalStaffService(MedicalStaffRepository medicalStaffRepository, IUserService userService,
                               UserRepository userRepository, ClinicRepository clinicRepository, ExaminationTypeRepository examinationTypeRepository) {
        _medicalStaffRepository = medicalStaffRepository;
        _userService = userService;
        _userRepository = userRepository;
        _clinicRepository = clinicRepository;
        _examinationTypeRepository = examinationTypeRepository;
    }

    @Override
    public MedicalStaffResponse createMedicalStaff(CreateMedicalStaffRequest request, Long clinicId) throws Exception {

        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword(request.getPassword());
        userRequest.setRePassword(request.getRePassword());
        userRequest.setAddress(request.getAddress());
        userRequest.setCity(request.getCity());
        userRequest.setEmail(request.getEmail());
        userRequest.setCountry(request.getCountry());
        userRequest.setFirstName(request.getFirstName());
        userRequest.setLastName(request.getLastName());
        userRequest.setSsn(request.getSsn());
        userRequest.setPhone(request.getPhone());
        userRequest.setUserType(UserType.MEDICAL);

        // Save user to database and gets NOT User Entity, we get UserResponse
        UserResponse userResponse = _userService.createUser(userRequest);
        // Transform to user entity
        User user = _userRepository.findOneById(userResponse.getId());
        Clinic clinic = _clinicRepository.findOneById(clinicId);

        MedicalStaff medicalStaff = new MedicalStaff();
        medicalStaff.setClinic(clinic);
        medicalStaff.setUser(user);
        medicalStaff.setMedicalType(request.getMedicalType());

        medicalStaff.setStartWorkAt(request.getStartAt());
        medicalStaff.setEndWorkAt(request.getEndAt());

        medicalStaff.setExaminationType(_examinationTypeRepository.findOneById(request.getExaminationTypeId()));

        MedicalStaff savedMedicalStaff = _medicalStaffRepository.save(medicalStaff);

        return mapMedicalToMedicalResponse(savedMedicalStaff);
    }

    @Override
    public MedicalStaffResponse updateMedicalStaff(Long id, UpdateMedicalStaffRequest request) throws Exception {
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);

        if (medicalStaff == null) {
            throw new Exception(String.format("Medical staff with %s id is not found", id));
        }

        User user = medicalStaff.getUser();
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setSsn(request.getSsn());

        MedicalStaff savedMedicalStaff = _medicalStaffRepository.save(medicalStaff);

        return mapMedicalToMedicalResponse(savedMedicalStaff);
    }

    @Override
    public MedicalStaffResponse getMedicalStaff(Long id) throws Exception {
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);

        if (medicalStaff == null) {
            throw new Exception(String.format("Patient with %s id is not found", id));
        }

        return mapMedicalToMedicalResponse(medicalStaff);
    }

    @Override
    public List<MedicalStaffResponse> getMedicalStaff() {
        List<MedicalStaff> medicalStaffs = _medicalStaffRepository.findAll();

        return medicalStaffs
                .stream()
                .map(medicalStaff -> mapMedicalToMedicalResponse(medicalStaff))
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalStaffResponse> getAllMedicalByClinic(Long id) throws Exception {
        Clinic clinic = _clinicRepository.findOneById(id);
        if (clinic == null) {
            throw new Exception(String.format("Clinic with % id not found", id.toString()));
        }

        List<MedicalStaff> medicalStaffs = _medicalStaffRepository.findAllByClinic(clinic);

        return medicalStaffs
                .stream()
                .map(medicalStaff -> mapMedicalToMedicalResponse(medicalStaff))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMedicalStaff(Long id) {
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);
        medicalStaff.getUser().setDeletedStatus(DeletedStatus.IS_DELETED);
        _medicalStaffRepository.save(medicalStaff);
    }

    @Override
    public List<MedicalStaffResponse> searchMedicalStaff(SearchMedicalStaffRequest searchMedicalStaffRequest) throws Exception {

        JPAQuery query = new JPAQuery(entityManager);

        if(searchMedicalStaffRequest.getFirstName() != null) {
            query.select(qMedicalStaff).where(qClinic.id.eq(qMedicalStaff.clinic.id)
                    .and(qMedicalStaff.user.firstName.containsIgnoreCase(searchMedicalStaffRequest.getFirstName())));
        }

        if(searchMedicalStaffRequest.getLastName() != null) {
            query.select(qMedicalStaff).where(qClinic.id.eq(qMedicalStaff.clinic.id)
                    .and(qMedicalStaff.user.lastName.containsIgnoreCase(searchMedicalStaffRequest.getLastName())));
        }

        if(searchMedicalStaffRequest.getExaminationType() != null) {
            query.select(qMedicalStaff).where(qClinic.id.eq(qMedicalStaff.clinic.id)
                    .and(qMedicalStaff.examinationType.name.containsIgnoreCase(searchMedicalStaffRequest.getExaminationType())));
        }

        List<MedicalStaff> medicalStaffs = query.fetch();
        return medicalStaffs
                .stream()
                .map(medicalStaff -> mapMedicalToMedicalResponse(medicalStaff))
                .collect(Collectors.toList());
    }

    private MedicalStaffResponse mapMedicalToMedicalResponse(MedicalStaff medicalStaff) {

        MedicalStaffResponse medicalStaffResponse = new MedicalStaffResponse();
        User user = medicalStaff.getUser();
        medicalStaffResponse.setEmail(user.getEmail());
        medicalStaffResponse.setId(medicalStaff.getId());
        medicalStaffResponse.setAddress(user.getAddress());
        medicalStaffResponse.setCity(user.getCity());
        medicalStaffResponse.setClinicId(medicalStaff.getClinic().getId());
        medicalStaffResponse.setCountry(user.getCountry());
        medicalStaffResponse.setFirstName(user.getFirstName());
        medicalStaffResponse.setLastName(user.getLastName());
        medicalStaffResponse.setPhone(user.getPhone());
        medicalStaffResponse.setSsn(user.getSsn());
        medicalStaffResponse.setMedicalType(medicalStaff.getMedicalType());
        medicalStaffResponse.setStartAt(medicalStaff.getStartWorkAt());
        medicalStaffResponse.setEndAt(medicalStaff.getEndWorkAt());
        medicalStaffResponse.setExaminationType(medicalStaff.getExaminationType().getName());

        return medicalStaffResponse;

    }
}
