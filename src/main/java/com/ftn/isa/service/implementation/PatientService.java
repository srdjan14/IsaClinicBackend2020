package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.CreatePatientRequest;
import com.ftn.isa.dto.request.CreateUserRequest;
import com.ftn.isa.dto.request.SearchPatientRequest;
import com.ftn.isa.dto.request.UpdatePatientRequest;
import com.ftn.isa.dto.response.PatientResponse;
import com.ftn.isa.dto.response.UserResponse;
import com.ftn.isa.entity.*;
import com.ftn.isa.repository.ExaminationRequestRepository;
import com.ftn.isa.repository.PatientRepository;
import com.ftn.isa.repository.UserRepository;
import com.ftn.isa.service.IPatientService;
import com.ftn.isa.service.IRegistrationRequestService;
import com.ftn.isa.service.IUserService;
import com.ftn.isa.utils.enums.DeletedStatus;
import com.ftn.isa.utils.enums.UserType;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    private final PatientRepository _patientRepository;

    private final IUserService _userService;

    private final UserRepository _userRepository;

    private final IRegistrationRequestService _registrationRequestService;

    private final ExaminationRequestRepository _examinationRequestRepository;

    public PatientService(PatientRepository patientRepository, IUserService userService, UserRepository userRepository, IRegistrationRequestService registrationRequestService, ExaminationRequestRepository examinationRequestRepository) {
        _patientRepository = patientRepository;
        _userService = userService;
        _userRepository = userRepository;
        _registrationRequestService = registrationRequestService;
        _examinationRequestRepository = examinationRequestRepository;
    }

    @Override
    public PatientResponse createPatient(CreatePatientRequest request) throws Exception {
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
        userRequest.setUserType(UserType.PATIENT);
        // Save user to database and gets NOT User Entity, we get UserResponse
        UserResponse userResponse = _userService.createUser(userRequest);
        // Transform to user entity
        User user = _userRepository.findOneById(userResponse.getId());

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setActive(false);

        Patient savedPatient = _patientRepository.save(patient);

        _registrationRequestService.createRegistrationRequest(request);
        return mapPatientToPatientResponse(savedPatient);
    }

    @Override
    public PatientResponse updatePatient(Long id, UpdatePatientRequest request) throws Exception {
        Patient patient = _patientRepository.findOneById(id);

        if (patient == null) {
            throw new Exception(String.format("Patient with %s id is not found", id));
        }

        User user = patient.getUser();
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        Patient savedPatient = _patientRepository.save(patient);

        return mapPatientToPatientResponse(savedPatient);
    }

    @Override
    public PatientResponse getPatient(Long id) throws Exception {
        Patient patient = _patientRepository.findOneById(id);

        if (patient == null) {
            throw new Exception(String.format("Patient with %s id is not found", id));
        }

        return mapPatientToPatientResponse(patient);
    }

    @Override
    public List<PatientResponse> getPatients() {
        List<Patient> patients = _patientRepository.findAll();

        return patients
                .stream()
                .map(patient -> mapPatientToPatientResponse(patient))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = _patientRepository.findOneById(id);
        patient.getUser().setDeletedStatus(DeletedStatus.IS_DELETED);
        _patientRepository.save(patient);
    }

    @Override
    public List<PatientResponse> getPatientsByClinic(Long clinicId) throws Exception {

        QPatient qPatient = QPatient.patient;
        QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
        JPAQuery query = _patientRepository.getQuery();

        query.select(qPatient).leftJoin(qExaminationRequest).on(qPatient.id.eq(qExaminationRequest.patient.id)).where(qExaminationRequest.patient.id.isNotNull()).distinct();
        query.where(qExaminationRequest.clinic.id.eq(clinicId));
        List<Patient> list = query.fetch();

        if(list.isEmpty()) {
            throw new Exception("Patient cannot be found in this clinic");
        }

        return list
                .stream()
                .map(patient -> mapPatientToPatientResponse(patient))
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientResponse> searchPatients(SearchPatientRequest request, Long clinicId) throws Exception {
        QPatient qPatient = QPatient.patient;
        QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
        JPAQuery query = _patientRepository.getQuery();

        query.select(qPatient).leftJoin(qExaminationRequest).on(qPatient.id.eq(qExaminationRequest.patient.id)).where(qExaminationRequest.patient.id.isNotNull()).distinct();
        query.where(qExaminationRequest.clinic.id.eq(clinicId));

        if(request.getFirstName() != null) {
            query.where(qPatient.user.firstName.containsIgnoreCase(request.getFirstName()));
        }

        if(request.getLastName() != null) {
            query.where(qPatient.user.lastName.containsIgnoreCase(request.getLastName()));
        }

        if(request.getSsn() != null) {
            query.where(qPatient.user.ssn.containsIgnoreCase(request.getSsn()));
        }

        List<Patient> list = query.fetch();
        if(list.isEmpty()) {
            throw new Exception("Patient isn't in this clinic");
        }

        return list
                .stream()
                .map(patient -> mapPatientToPatientResponse(patient))
                .collect(Collectors.toList());
    }

    private PatientResponse mapPatientToPatientResponse(Patient patient) {
        PatientResponse patientResponse = new PatientResponse();
        User user = patient.getUser();
        patientResponse.setEmail(user.getEmail());
        patientResponse.setId(patient.getId());
        patientResponse.setAddress(user.getAddress());
        patientResponse.setCity(user.getCity());
        patientResponse.setCountry(user.getCountry());
        patientResponse.setFirstName(user.getFirstName());
        patientResponse.setLastName(user.getLastName());
        patientResponse.setPhone(user.getPhone());
        patientResponse.setSsn(user.getSsn());
        return patientResponse;
    }
}
