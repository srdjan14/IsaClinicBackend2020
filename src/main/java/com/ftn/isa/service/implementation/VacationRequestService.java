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
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class VacationRequestService implements IVacationRequestService {

    @PersistenceContext
    private EntityManager entityManager;

    private QVacationRequest qVacationRequest = QVacationRequest.vacationRequest;

    private QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;

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

    @Override
    public VacationRequestResponse createVacationRequest(CreateVacationRequest request) {
        VacationRequest vacationRequest = new VacationRequest();

        JPAQuery query = new JPAQuery(entityManager);

        query.select(qExaminationRequest);

        if(query.where(qExaminationRequest.examinationDate.between(request.getStartAt(), request.getEndAt())) != null) {
            try {
                throw new Exception("Date not available because there is upcoming examination");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        vacationRequest.setDescription(request.getDescription());
        vacationRequest.setConfirmed(false);
        vacationRequest.setStartAt(request.getStartAt());
        vacationRequest.setEndAt(request.getEndAt());

        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(request.getMedicalStaffId());
        vacationRequest.setMedicalStaff(medicalStaff);

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
        List<VacationRequest> vacationRequests = _vacationRequestRepository.findAll();

        return vacationRequests
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

        vacationRequest.setConfirmed(true);
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

        vacationRequest.setConfirmed(false);
        _vacationRequestRepository.save(vacationRequest);

        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(vacationRequest.getMedicalStaff().getId());
        _emailService.sendAcceptedVacation(medicalStaff.getUser());
    }

    @Override
    public List<VacationRequestResponse> getAllVacationRequestsByClinic(Long id) throws Exception {
        Clinic clinic = _clinicRepository.findOneById(id);
        if (clinic == null) {
            throw new Exception(String.format("Clinic with % id not found", id.toString()));
        }

        List<VacationRequest> vacationRequests = _vacationRequestRepository.findAllByClinic(id);

        return vacationRequests
                .stream()
                .map(vacationRequest -> mapVacationRequestToVacationRequestResponse(vacationRequest))
                .collect(Collectors.toList());
    }

    private VacationRequestResponse mapVacationRequestToVacationRequestResponse(VacationRequest vacationRequest) {
        VacationRequestResponse vacationRequestResponse = new VacationRequestResponse();
        vacationRequestResponse.setDescription(vacationRequest.getDescription());
        vacationRequestResponse.setStartAt(vacationRequest.getStartAt());
        vacationRequestResponse.setEndAt(vacationRequest.getEndAt());
        vacationRequestResponse.setConfirmed(vacationRequest.isConfirmed());
        vacationRequestResponse.setMedicalStaffId(vacationRequest.getMedicalStaff().getId());
        vacationRequestResponse.setId(vacationRequest.getId());
        return vacationRequestResponse;
    }
}
