package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.ClinicReviewRequest;
import com.ftn.isa.dto.request.DoctorReviewRequest;
import com.ftn.isa.dto.response.ClinicReviewResponse;
import com.ftn.isa.dto.response.DoctorReviewResponse;
import com.ftn.isa.entity.*;
import com.ftn.isa.repository.*;
import com.ftn.isa.service.IReviewService;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ReviewService implements IReviewService {

    private final DoctorReviewRepository _doctorReviewRepository;

    private final MedicalStaffRepository _medicalStaffRepository;

    private final PatientRepository _patientRepository;

    private final ClinicRepository _clinicRepository;

    private final ClinicReviewRepository _clinicReviewRepository;

    public ReviewService(DoctorReviewRepository doctorReviewRepository, MedicalStaffRepository medicalStaffRepository, PatientRepository patientRepository, ClinicRepository clinicRepository, ClinicReviewRepository clinicReviewRepository) {
        _doctorReviewRepository = doctorReviewRepository;
        _medicalStaffRepository = medicalStaffRepository;
        _patientRepository = patientRepository;
        _clinicRepository = clinicRepository;
        _clinicReviewRepository = clinicReviewRepository;
    }

    @Override
    public DoctorReviewResponse reviewingDoctor(DoctorReviewRequest request) throws Exception {
        Patient patient = _patientRepository.findOneById(request.getPatientId());

        AtomicBoolean flag = new AtomicBoolean(false);

        patient.getDoctorReviewList().forEach(doctorReview -> {
            if (doctorReview.getMedicalStaff() != null) {
                if(request.getMedicalStaffId().equals(doctorReview.getMedicalStaff().getId())) {
                    if(request.getPatientId().equals(doctorReview.getPatient().getId())) {
                       flag.set(true);
                    }
                }
            }
        });

        if(flag.get() == true) {
            throw new Exception("Doctor is already rated!");
        }

        DoctorReview doctorReview = new DoctorReview();
        doctorReview.setReview(request.getReview());
        doctorReview.setDescription(request.getDescription());
        doctorReview.setPatient(patient);

        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(request.getMedicalStaffId());

        doctorReview.setMedicalStaff(medicalStaff);

        DoctorReview savedDoctorReview = _doctorReviewRepository.save(doctorReview);

        return mapReviewToDoctorReviewResponse(savedDoctorReview);
    }

    @Override
    public ClinicReviewResponse reviewingClinic(ClinicReviewRequest request) throws Exception {
        Patient patient = _patientRepository.findOneById(request.getPatientId());

        AtomicBoolean flag = new AtomicBoolean(false);

        patient.getClinicReviewList().forEach(clinicReview -> {
            if (clinicReview.getClinic() != null) {
                if(request.getClinicId().equals(clinicReview.getClinic().getId())) {
                    if(request.getPatientId().equals(clinicReview.getPatient().getId())) {
                        flag.set(true);
                    }
                }
            }
        });

        if(flag.get() == true)  {
            throw new Exception("Clinic is already rated!");
        }

        ClinicReview clinicReview = new ClinicReview();
        clinicReview.setReview(request.getReview());
        clinicReview.setDescription(request.getDescription());
        clinicReview.setPatient(patient);

        Clinic clinic = _clinicRepository.findOneById(request.getClinicId());

        clinicReview.setClinic(clinic);

        ClinicReview savedClinicReview = _clinicReviewRepository.save(clinicReview);

        return mapReviewToClinicReviewResponse(savedClinicReview);
    }

    @Override
    public Double averageDoctorRating(Long id) {
        QDoctorReview qDoctorReview = QDoctorReview.doctorReview;
        JPAQuery query = _doctorReviewRepository.getQuery();

        query.select(qDoctorReview.review).where(qDoctorReview.medicalStaff.id.eq(id));

        List<Double> list = query.fetch();

        Double sum = list
                    .stream().mapToDouble(Double::doubleValue)
                    .sum();

        Integer size = list.size();

        Double average = sum/size;

        return average;
    }

    @Override
    public Double averageClinicRating(Long id) {
        QClinicReview qClinicReview = QClinicReview.clinicReview;
        JPAQuery query = _clinicReviewRepository.getQuery();

        query.select(qClinicReview.review).where(qClinicReview.clinic.id.eq(id));

        List<Double> list = query.fetch();

        Double sum = list
                    .stream().mapToDouble(Double::doubleValue)
                    .sum();

        Integer size = list.size();

        Double average = sum/size;

        return average;
    }

    private DoctorReviewResponse mapReviewToDoctorReviewResponse(DoctorReview doctorReview) {
        DoctorReviewResponse doctorReviewResponse = new DoctorReviewResponse();
        doctorReviewResponse.setDescription(doctorReview.getDescription());
        doctorReviewResponse.setReview(doctorReview.getReview());
        doctorReviewResponse.setMedicalStaffId(doctorReview.getMedicalStaff().getId());
        doctorReviewResponse.setPatientId(doctorReview.getPatient().getId());
        doctorReviewResponse.setId(doctorReview.getId());
        return doctorReviewResponse;
    }

    private ClinicReviewResponse mapReviewToClinicReviewResponse(ClinicReview clinicReview) {
        ClinicReviewResponse clinicReviewResponse = new ClinicReviewResponse();
        clinicReviewResponse.setDescription(clinicReview.getDescription());
        clinicReviewResponse.setReview(clinicReview.getReview());
        clinicReviewResponse.setClinicId(clinicReview.getClinic().getId());
        clinicReviewResponse.setPatientId(clinicReview.getPatient().getId());
        clinicReviewResponse.setId(clinicReview.getId());
        return clinicReviewResponse;
    }
}
