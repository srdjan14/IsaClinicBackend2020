package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.CreateExaminationRequest;
import com.ftn.isa.dto.response.ExaminationRequestResponse;
import com.ftn.isa.entity.*;
import com.ftn.isa.repository.*;
import com.ftn.isa.service.IExaminationRequestService;
import com.ftn.isa.utils.enums.RequestStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExaminationRequestService implements IExaminationRequestService {

    private final ClinicRepository _clinicRepository;

    private final MedicalStaffRepository _medicalStaffRepository;

    private final ExaminationRequestRepository _examinationRequestRepository;

    private final ExaminationTypeRepository _examinationTypeRepository;

    private final PatientRepository _patientRepository;

    private final OperationRoomRepository _operationRoomRepository;

    public ExaminationRequestService(ClinicRepository clinicRepository, MedicalStaffRepository medicalStaffRepository, ExaminationRequestRepository examinationRequestRepository, ExaminationTypeRepository examinationTypeRepository, PatientRepository patientRepository, OperationRoomRepository operationRoomRepository) {
        _clinicRepository = clinicRepository;
        _medicalStaffRepository = medicalStaffRepository;
        _examinationRequestRepository = examinationRequestRepository;
        _examinationTypeRepository = examinationTypeRepository;
        _patientRepository = patientRepository;
        _operationRoomRepository = operationRoomRepository;
    }

    @Override
    public List<ExaminationRequestResponse> getAllExaminationRequest() {
        List<ExaminationRequest> examinationRequests = _examinationRequestRepository.findAll();

        return examinationRequests
                .stream()
                .map(examinationRequest -> mapExaminationRequestToExaminationResponse(examinationRequest))
                .collect(Collectors.toList());
    }

    @Override
    public ExaminationRequestResponse createPredefinedExaminationRequest(CreateExaminationRequest request) {
        ExaminationRequest examinationRequest = new ExaminationRequest();
        examinationRequest.setStartAt(request.getStartAt());
        examinationRequest.setEndAt(request.getStartAt().plusHours(1));
        examinationRequest.setStatus(RequestStatus.APPROVED);
        examinationRequest.setExaminationDate(request.getExaminationDate());

        Clinic clinic = _clinicRepository.findOneById(request.getClinicId());
        examinationRequest.setClinic(clinic);

        MedicalStaff doctor = _medicalStaffRepository.findOneById(request.getDoctorId());
        examinationRequest.setMedicalStaff(doctor);

        ExaminationType examinationType = _examinationTypeRepository.findOneById(request.getExaminationTypeId());
        examinationRequest.setExaminationType(examinationType);
        examinationRequest.setPrice(examinationType.getPrice());

        OperationRoom operationRoom = _operationRoomRepository.findOneById(request.getOperationRoomId());
        examinationRequest.setOperationRoom(operationRoom);

        ExaminationRequest savedExaminationRequest = _examinationRequestRepository.save(examinationRequest);

        return mapExaminationRequestToExaminationResponse(savedExaminationRequest);
    }

    @Override
    public void bookingPredefinedExamination(Long patientId, Long examinationRequestId) {
        ExaminationRequest examinationRequest = _examinationRequestRepository.findOneById(examinationRequestId);

        Patient patient = _patientRepository.findOneById(patientId);
        examinationRequest.setPatient(patient);

      _examinationRequestRepository.save(examinationRequest);


    }

    @Override
    public List<ExaminationRequestResponse> findAllExaminationOfPatient(Long id) {
        List<ExaminationRequest> examinationRequests = _examinationRequestRepository.findAllByPatient_Id(id);

        return examinationRequests
                .stream()
                .map(request -> mapExaminationRequestToExaminationResponse(request))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExaminationRequestResponse> getAllExaminationsByMedical(Long id) {
        List<ExaminationRequest> examinationRequests = _examinationRequestRepository.findAllByMedicalStaff_Id(id);

        return examinationRequests
                .stream()
                .map(request -> mapExaminationRequestToExaminationResponse(request))
                .collect(Collectors.toList());
    }

    private ExaminationRequestResponse mapExaminationRequestToExaminationResponse(ExaminationRequest examinationRequest) {
        ExaminationRequestResponse examinationRequestResponse = new ExaminationRequestResponse();
        examinationRequestResponse.setStartAt(examinationRequest.getStartAt());
        examinationRequestResponse.setEndAt(examinationRequest.getEndAt());
        examinationRequestResponse.setExaminationDate(examinationRequest.getExaminationDate());
        examinationRequestResponse.setExaminationTypeId(examinationRequest.getExaminationType().getId());
        examinationRequestResponse.setExaminationTypeName(examinationRequest.getExaminationType().getName());
        examinationRequestResponse.setRequestStatus(examinationRequest.getStatus());
        examinationRequestResponse.setId(examinationRequest.getId());
        examinationRequestResponse.setPrice(examinationRequest.getExaminationType().getPrice());
        return examinationRequestResponse;
    }
}
