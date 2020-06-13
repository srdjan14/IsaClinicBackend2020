package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.CreateAvailableExaminationsRequest;
import com.ftn.isa.dto.request.CreateExaminationRequest;
import com.ftn.isa.dto.request.SearchDoctorForExaminationRequest;
import com.ftn.isa.dto.request.SearchExaminationRequest;
import com.ftn.isa.dto.response.ExaminationRequestResponse;
import com.ftn.isa.dto.response.MedicalStaffResponse;
import com.ftn.isa.dto.response.PredefinedExaminationResponse;
import com.ftn.isa.entity.*;
import com.ftn.isa.repository.*;
import com.ftn.isa.service.IEmailService;
import com.ftn.isa.service.IExaminationRequestService;
import com.ftn.isa.utils.enums.RequestStatus;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
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

    private final IEmailService _emailService;

    private final VacationRequestRepository _vacationRequestRepository;


    public ExaminationRequestService(ClinicRepository clinicRepository, MedicalStaffRepository medicalStaffRepository, ExaminationRequestRepository examinationRequestRepository, ExaminationTypeRepository examinationTypeRepository, PatientRepository patientRepository, OperationRoomRepository operationRoomRepository, IEmailService emailService, VacationRequestRepository vacationRequestRepository) {
        _clinicRepository = clinicRepository;
        _medicalStaffRepository = medicalStaffRepository;
        _examinationRequestRepository = examinationRequestRepository;
        _examinationTypeRepository = examinationTypeRepository;
        _patientRepository = patientRepository;
        _operationRoomRepository = operationRoomRepository;
        _emailService = emailService;
        _vacationRequestRepository = vacationRequestRepository;
    }

    @Override
    public List<ExaminationRequestResponse> getAllExaminationRequest() {
        List<ExaminationRequest> examinationRequests = _examinationRequestRepository.findAll();

        return examinationRequests
                .stream()
                .map(examinationRequest -> mapExaminationRequestToExaminationResponse(examinationRequest))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public PredefinedExaminationResponse createPredefinedExaminationRequest(CreateExaminationRequest request) throws Exception {
        ExaminationRequest examinationRequest = new ExaminationRequest();
        examinationRequest.setStatus(RequestStatus.APPROVED);
        examinationRequest.setExaminationDate(request.getExaminationDate());

        Clinic clinic = _clinicRepository.findOneById(request.getClinicId());
        examinationRequest.setClinic(clinic);

        MedicalStaff doctor = _medicalStaffRepository.findOneById(request.getDoctorId());
        examinationRequest.setMedicalStaff(doctor);

        if(!doctor.getStartWorkAt().isBefore(request.getStartAt()) && doctor.getEndWorkAt().isAfter(request.getStartAt().plusHours(1))) {
            throw new Exception("Doctor doesn't work at that hours");
        }

        QMedicalStaff qMedicalStaff = QMedicalStaff.medicalStaff;
        QVacationRequest qVacationRequest = QVacationRequest.vacationRequest;

        JPAQuery query = _medicalStaffRepository.getQuery();

        query.select(qMedicalStaff).where(qMedicalStaff.id.eq(request.getDoctorId()));
        query.leftJoin(qVacationRequest).on(qMedicalStaff.id.eq(qVacationRequest.medicalStaff.id));
        query.where(qVacationRequest.startAt.before(request.getExaminationDate()));
        query.where(qVacationRequest.endAt.after(request.getExaminationDate()));
        query.where(qVacationRequest.requestStatus.eq(RequestStatus.APPROVED));

        List<VacationRequest> list = query.fetch();
        if(!list.isEmpty()) {
            throw new Exception("Doctor is on vacation");
        }

        QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
        JPAQuery query1 = _medicalStaffRepository.getQuery();

        query1.select(qMedicalStaff).where(qMedicalStaff.id.eq(request.getDoctorId()));
        query1.leftJoin(qExaminationRequest).on(qMedicalStaff.id.eq(qExaminationRequest.medicalStaff.id));
        query1.where(qExaminationRequest.patient.id.isNotNull());
        query1.where(qExaminationRequest.startAt.after(request.getStartAt()));
        query1.where(qExaminationRequest.endAt.before(request.getStartAt().plusHours(1)));

        List<ExaminationRequest> list1 = query1.fetch();
        if(!list1.isEmpty()) {
            throw new Exception("Doctor already has examination at that time");
        }

        examinationRequest.setStartAt(request.getStartAt());
        examinationRequest.setEndAt(request.getStartAt().plusHours(1));

        ExaminationType examinationType = _examinationTypeRepository.findOneById(request.getExaminationTypeId());
        examinationRequest.setExaminationType(examinationType);
        examinationRequest.setPrice(examinationType.getPrice());

        OperationRoom operationRoom = _operationRoomRepository.findOneById(request.getOperationRoomId());
        examinationRequest.setOperationRoom(operationRoom);

        ExaminationRequest savedExaminationRequest = _examinationRequestRepository.save(examinationRequest);

        return mapPredefinedRequestToExaminationResponse(savedExaminationRequest);
    }

    @Transactional(readOnly = false)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    public void bookingPredefinedExamination(Long patientId, Long examinationRequestId) {
        ExaminationRequest examinationRequest = _examinationRequestRepository.findOneById(examinationRequestId);

        Patient patient = _patientRepository.findOneById(patientId);
        examinationRequest.setPatient(patient);

        _examinationRequestRepository.save(examinationRequest);

        _emailService.sendConfirmedExamination(patient.getUser());
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

    @Override
    public List<ExaminationRequestResponse> searchExaminationRequest(SearchExaminationRequest request) throws Exception {
       QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
       QClinic qClinic = QClinic.clinic;
        JPAQuery query = _examinationRequestRepository.getQuery();

        query.select(qExaminationRequest).leftJoin(qClinic).on(qExaminationRequest.clinic.id.eq(qClinic.id)).where(qClinic.id.isNotNull());

        if(request.getExaminationTypeName() != null) {
            query.where(qExaminationRequest.examinationType.name.containsIgnoreCase(request.getExaminationTypeName()));
        }

        if(request.getPrice() != 0) {
            query.where(qExaminationRequest.price.in(request.getPrice()));
        }

        if(request.getExaminationDate() != null) {
            query.where(qExaminationRequest.examinationDate.eq(request.getExaminationDate()));
        }

        List<ExaminationRequest> list = query.fetch();
        if(list.isEmpty()) {
            throw new Exception("Can't find examination");
        }
        return list
                .stream()
                .map(examinationRequest -> mapExaminationRequestToExaminationResponse(examinationRequest))
                .collect(Collectors.toList());
    }

    @Override
    public ExaminationRequestResponse createAvailableExaminations(CreateAvailableExaminationsRequest request) throws Exception {
        ExaminationRequest examinationRequest = new ExaminationRequest();
        examinationRequest.setStatus(RequestStatus.PENDING);
        examinationRequest.setExaminationDate(request.getExaminationDate());

        Clinic clinic = _clinicRepository.findOneById(request.getClinicId());
        examinationRequest.setClinic(clinic);

        MedicalStaff doctor = _medicalStaffRepository.findOneById(request.getDoctorId());
        examinationRequest.setMedicalStaff(doctor);

        if(!doctor.getStartWorkAt().isBefore(request.getStartAt()) && doctor.getEndWorkAt().isAfter(request.getStartAt().plusHours(1))) {
            throw new Exception("Doctor doesn't work at that hours");
        }

        QMedicalStaff qMedicalStaff = QMedicalStaff.medicalStaff;
        QVacationRequest qVacationRequest = QVacationRequest.vacationRequest;

        JPAQuery query = _medicalStaffRepository.getQuery();

        query.select(qMedicalStaff).where(qMedicalStaff.id.eq(request.getDoctorId()));
        query.leftJoin(qVacationRequest).on(qMedicalStaff.id.eq(qVacationRequest.medicalStaff.id));
        query.where(qVacationRequest.startAt.after(request.getExaminationDate()));
        query.where(qVacationRequest.endAt.before(request.getExaminationDate()));
        query.where(qVacationRequest.requestStatus.eq(RequestStatus.APPROVED));

        List<VacationRequest> list = query.fetch();
        if(!list.isEmpty()) {
            throw new Exception("Doctor is on vacation");
        }

        QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
        JPAQuery query1 = _medicalStaffRepository.getQuery();

        query1.select(qMedicalStaff).where(qMedicalStaff.id.eq(request.getDoctorId()));
        query1.leftJoin(qExaminationRequest).on(qMedicalStaff.id.eq(qExaminationRequest.medicalStaff.id));
        query1.where(qExaminationRequest.patient.id.isNotNull());
        query1.where(qExaminationRequest.startAt.before(request.getStartAt()));
        query1.where(qExaminationRequest.endAt.after(request.getStartAt().plusHours(1)));

        List<ExaminationRequest> list1 = query1.fetch();
        if(!list1.isEmpty()) {
            throw new Exception("Doctor already has examination at that time");
        }

        examinationRequest.setStartAt(request.getStartAt());
        examinationRequest.setEndAt(request.getStartAt().plusHours(1));

        ExaminationType examinationType = _examinationTypeRepository.findOneById(request.getExaminationTypeId());
        examinationRequest.setExaminationType(examinationType);
        examinationRequest.setPrice(examinationType.getPrice());

        ExaminationRequest savedExaminationRequest = _examinationRequestRepository.save(examinationRequest);

        return mapExaminationRequestToExaminationResponse(savedExaminationRequest);
    }

    @Override
    public List<ExaminationRequestResponse> getAllByClinic(Long id) {
        QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
        JPAQuery query = _examinationRequestRepository.getQuery();

        query.select(qExaminationRequest).where(qExaminationRequest.clinic.id.eq(id));
        query.where(qExaminationRequest.status.eq(RequestStatus.PENDING));
        query.where(qExaminationRequest.patient.id.isNotNull());
        List<ExaminationRequest> list = query.fetch();

        return list
                .stream()
                .map(examinationRequest -> mapExaminationRequestToExaminationResponse(examinationRequest))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExaminationRequestResponse> getAvailableExaminationsOfDoctor(SearchDoctorForExaminationRequest request, Long id) {
        QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
        JPAQuery query = _examinationRequestRepository.getQuery();

        query.select(qExaminationRequest).where(qExaminationRequest.medicalStaff.id.eq(id)).where(qExaminationRequest.status.eq(RequestStatus.PENDING));
        query.where(qExaminationRequest.patient.id.isNull());
        query.where(qExaminationRequest.examinationDate.eq(request.getExaminationDate()));
        query.where(qExaminationRequest.examinationType.eq(request.getExaminationType())).distinct();

        List<ExaminationRequest> list = query.fetch();

        return list
                .stream()
                .map(examinationRequest -> mapExaminationRequestToExaminationResponse(examinationRequest))
                .collect(Collectors.toList());
    }

    @Override
    public void bookingAvailableExamination(Long patientId, Long examinationRequestId) {
        ExaminationRequest examinationRequest = _examinationRequestRepository.findOneById(examinationRequestId);

        Patient patient = _patientRepository.findOneById(patientId);
        examinationRequest.setPatient(patient);

        _examinationRequestRepository.save(examinationRequest);
    }

    public void declineExaminationRequest(Long clinicId, )

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

    private PredefinedExaminationResponse mapPredefinedRequestToExaminationResponse(ExaminationRequest examinationRequest) {
        PredefinedExaminationResponse predefinedExaminationResponse = new PredefinedExaminationResponse();
        predefinedExaminationResponse.setStartAt(examinationRequest.getStartAt());
        predefinedExaminationResponse.setEndAt(examinationRequest.getEndAt());
        predefinedExaminationResponse.setExaminationDate(examinationRequest.getExaminationDate());
        predefinedExaminationResponse.setExaminationTypeId(examinationRequest.getExaminationType().getId());
        predefinedExaminationResponse.setExaminationTypeName(examinationRequest.getExaminationType().getName());
        predefinedExaminationResponse.setRequestStatus(examinationRequest.getStatus());
        predefinedExaminationResponse.setId(examinationRequest.getId());
        predefinedExaminationResponse.setPrice(examinationRequest.getExaminationType().getPrice());
        predefinedExaminationResponse.setOperationRoomId(examinationRequest.getOperationRoom().getId());
        return predefinedExaminationResponse;
    }
}
