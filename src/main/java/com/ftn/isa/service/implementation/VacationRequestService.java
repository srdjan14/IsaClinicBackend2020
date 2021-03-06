package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.CreateVacationRequest;
import com.ftn.isa.dto.response.VacationRequestResponse;
import com.ftn.isa.entity.*;
import com.ftn.isa.repository.ClinicRepository;
import com.ftn.isa.repository.MedicalStaffRepository;
import com.ftn.isa.repository.VacationRequestRepository;
import com.ftn.isa.service.IEmailService;
import com.ftn.isa.service.IMedicalStaffService;
import com.ftn.isa.service.IVacationRequestService;
import com.ftn.isa.utils.enums.RequestStatus;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class VacationRequestService implements IVacationRequestService {

    @PersistenceContext
    private EntityManager entityManager;

    private final MedicalStaffRepository _medicalStaffRepository;

    private final VacationRequestRepository _vacationRequestRepository;

    private final IEmailService _emailService;

    private final ClinicRepository _clinicRepository;

    private final IMedicalStaffService _medicalStaffService;

    public VacationRequestService(MedicalStaffRepository medicalStaffRepository, VacationRequestRepository vacationRequestRepository, IEmailService emailService, ClinicRepository clinicRepository, IMedicalStaffService medicalStaffService) {
        _medicalStaffRepository = medicalStaffRepository;
        _vacationRequestRepository = vacationRequestRepository;
        _emailService = emailService;
        _clinicRepository = clinicRepository;
        _medicalStaffService = medicalStaffService;
    }

    @Transactional(readOnly = false)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    public VacationRequestResponse createVacationRequest(CreateVacationRequest request) throws Exception {
        VacationRequest vacationRequest = new VacationRequest();

        QVacationRequest qVacationRequest = QVacationRequest.vacationRequest;
        QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
        QMedicalStaff qMedicalStaff = QMedicalStaff.medicalStaff;
        JPAQuery query = _vacationRequestRepository.getQuery();

        query.select(qVacationRequest).leftJoin(qMedicalStaff).on(qVacationRequest.medicalStaff.id.eq(qMedicalStaff.id))
                .leftJoin(qExaminationRequest).on(qMedicalStaff.id.eq(qExaminationRequest.medicalStaff.id));
        query.where(qExaminationRequest.examinationDate.between(request.getStartAt(), request.getEndAt()));

        List<ExaminationRequest> list = query.fetch();
        if(!list.isEmpty()) {
            throw new Exception("Date not available because there is upcoming examination");
        }

        vacationRequest.setDescription(request.getDescription());
        vacationRequest.setRequestStatus(RequestStatus.PENDING);
        vacationRequest.setStartAt(request.getStartAt());
        vacationRequest.setEndAt(request.getEndAt());

        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(request.getMedicalStaffId());
        vacationRequest.setMedicalStaff(medicalStaff);

        Clinic clinic = _clinicRepository.findOneById(medicalStaff.getClinic().getId());
        vacationRequest.setClinic(clinic);

        VacationRequest savedVacationRequest = _vacationRequestRepository.save(vacationRequest);
        return mapVacationRequestToVacationRequestResponse(savedVacationRequest);
    }

    @Override
    public VacationRequestResponse getVacationRequest(Long id) throws Exception {
        VacationRequest vacationRequest = _vacationRequestRepository.findOneById(id);

        if (vacationRequest == null) {
            throw new Exception("Vacation request isn't found");
        }

        return mapVacationRequestToVacationRequestResponse(vacationRequest);
    }

    @Override
    public List<VacationRequestResponse> getVacationRequests() {
        QVacationRequest qVacationRequest = QVacationRequest.vacationRequest;
        JPAQuery query = _vacationRequestRepository.getQuery();

        query.select(qVacationRequest).where(qVacationRequest.requestStatus.eq(RequestStatus.PENDING));
        List<VacationRequest> list = query.fetch();

        return list
                .stream()
                .map(vacationRequest -> mapVacationRequestToVacationRequestResponse(vacationRequest))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void approveVacationRequest(Long id) throws Exception {
        VacationRequest vacationRequest = _vacationRequestRepository.findOneById(id);

        if(vacationRequest == null) {
            throw new Exception("Request doesn't exist");
        }

        vacationRequest.setRequestStatus(RequestStatus.APPROVED);
        _vacationRequestRepository.save(vacationRequest);

        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(vacationRequest.getMedicalStaff().getId());
        _emailService.sendAcceptedVacation(medicalStaff.getUser());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void declineVacationRequest(Long id) throws Exception {
        VacationRequest vacationRequest = _vacationRequestRepository.findOneById(id);

        if(vacationRequest == null) {
            throw new Exception("Request doesn't exist");
        }

        vacationRequest.setRequestStatus(RequestStatus.DENIED);
        _vacationRequestRepository.save(vacationRequest);

        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(vacationRequest.getMedicalStaff().getId());
        _emailService.sendDeniedVacation(medicalStaff.getUser());
    }

    @Override
    public List<VacationRequestResponse> getAllVacationRequestsByClinic(Long id) throws Exception {
        QVacationRequest qVacationRequest = QVacationRequest.vacationRequest;
        JPAQuery query = _vacationRequestRepository.getQuery();

        query.select(qVacationRequest).where(qVacationRequest.clinic.id.eq(id));
        query.where(qVacationRequest.requestStatus.eq(RequestStatus.PENDING));
        List<VacationRequest> list = query.fetch();

        return list
                .stream()
                .map(vacationRequest -> mapVacationRequestToVacationRequestResponse(vacationRequest))
                .collect(Collectors.toList());
    }

    private VacationRequestResponse mapVacationRequestToVacationRequestResponse(VacationRequest vacationRequest) {
        VacationRequestResponse vacationRequestResponse = new VacationRequestResponse();
        vacationRequestResponse.setDescription(vacationRequest.getDescription());
        vacationRequestResponse.setStartAt(vacationRequest.getStartAt());
        vacationRequestResponse.setEndAt(vacationRequest.getEndAt());
        vacationRequestResponse.setRequestStatus(vacationRequest.getRequestStatus());
        vacationRequestResponse.setMedicalStaffId(vacationRequest.getMedicalStaff().getId());
        vacationRequestResponse.setId(vacationRequest.getId());
        vacationRequestResponse.setClinicId(vacationRequest.getClinic().getId());
        return vacationRequestResponse;
    }
}
