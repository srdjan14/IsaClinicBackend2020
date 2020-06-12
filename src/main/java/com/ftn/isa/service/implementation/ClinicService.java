package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.ClinicRequest;
import com.ftn.isa.dto.request.SearchDoctorForExaminationRequest;
import com.ftn.isa.dto.response.ClinicResponse;
import com.ftn.isa.dto.response.ExaminationRequestResponse;
import com.ftn.isa.entity.*;
import com.ftn.isa.repository.ClinicRepository;
import com.ftn.isa.service.IClinicService;
import com.ftn.isa.utils.enums.RequestStatus;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicService implements IClinicService {

    private final ClinicRepository _clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        _clinicRepository = clinicRepository;
    }

    @Override
    public ClinicResponse createClinic(ClinicRequest request) throws Exception {
        Clinic clinic = new Clinic();

        clinic.setAddress(request.getAddress());
        clinic.setDescription(request.getDescription());
        clinic.setName(request.getName());
        clinic.setX(request.getX());
        clinic.setY(request.getY());

        Clinic savedClinic = _clinicRepository.save(clinic);
        return mapClinicToClinicResponse(savedClinic);
    }

    @Override
    public ClinicResponse updateClinic(Long id, ClinicRequest request) throws Exception {
        Clinic clinic = _clinicRepository.findOneById(id);

        if (clinic == null) {
            throw new Exception(String.format("Clinic with %s id is not found", id));
        }

        clinic.setAddress(request.getAddress());
        clinic.setDescription(request.getDescription());
        clinic.setName(request.getName());

        Clinic savedClinic = _clinicRepository.save(clinic);
        return mapClinicToClinicResponse(savedClinic);
    }

    @Override
    public ClinicResponse getClinic(Long id) throws Exception {
        Clinic clinic = _clinicRepository.findOneById(id);

        if (clinic == null) {
            throw new Exception("Clinic isn't found");
        }

        return mapClinicToClinicResponse(clinic);
    }

    @Override
    public List<ClinicResponse> getClinics() {
        List<Clinic> clinics = _clinicRepository.findAll();

        return clinics
                .stream()
                .map(clinic -> mapClinicToClinicResponse(clinic))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClinicResponse> searchClinic(ClinicRequest request) {
        QClinic qClinic = QClinic.clinic;

        JPAQuery query = _clinicRepository.getQuery();

        query.select(qClinic);

        if(request.getName() != null) {
            query.where(qClinic.name.containsIgnoreCase(request.getName()));
        }

        if(request.getAddress() != null) {
            query.where(qClinic.address.containsIgnoreCase(request.getAddress()));
        }

        if(request.getDescription() != null) {
            query.where(qClinic.description.containsIgnoreCase(request.getDescription()));
        }

        List<Clinic> clinics = query.fetch();

        return clinics
                .stream()
                .map(clinic -> mapClinicToClinicResponse(clinic))
                .collect(Collectors.toList());
    }


    @Override
    public List<ClinicResponse> searchFreeDoctorInClinic(SearchDoctorForExaminationRequest request) throws Exception {
        QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
        QClinic qClinic = QClinic.clinic;
        QVacationRequest qVacationRequest = QVacationRequest.vacationRequest;
        QMedicalStaff qMedicalStaff = QMedicalStaff.medicalStaff;

        JPAQuery query = _clinicRepository.getQuery();

        query.select(qClinic).leftJoin(qMedicalStaff).on(qClinic.id.eq(qMedicalStaff.clinic.id)).where(qMedicalStaff.clinic.id.isNotNull());
        query.leftJoin(qExaminationRequest).on(qMedicalStaff.id.eq(qExaminationRequest.medicalStaff.id)).where(qExaminationRequest.medicalStaff.id.isNotNull());
        query.leftJoin(qVacationRequest).on(qMedicalStaff.id.eq(qVacationRequest.medicalStaff.id)).where(qVacationRequest.medicalStaff.id.isNotNull());
        query.where(qVacationRequest.requestStatus.isNull());
        query.where(qVacationRequest.requestStatus.eq(RequestStatus.PENDING));

        List<Clinic> list = query.fetch();
        if(list.isEmpty()) {
            throw new Exception("There aren't free doctors in any clinic at asked date.");
        }

        return list
                .stream()
                .map(clinic -> mapClinicToClinicResponse(clinic))
                .collect(Collectors.toList());
    }

    private ClinicResponse mapClinicToClinicResponse(Clinic clinic) {
        ClinicResponse clinicResponse = new ClinicResponse();
        clinicResponse.setAddress(clinic.getAddress());
        clinicResponse.setDescription(clinic.getDescription());
        clinicResponse.setName(clinic.getName());
        clinicResponse.setId(clinic.getId());
        clinicResponse.setX(clinic.getX());
        clinicResponse.setY(clinic.getY());

        return clinicResponse;
    }


}
