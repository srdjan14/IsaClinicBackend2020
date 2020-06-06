package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.ClinicReviewRequest;
import com.ftn.isa.dto.request.DoctorReviewRequest;
import com.ftn.isa.dto.response.ClinicReviewResponse;
import com.ftn.isa.dto.response.DoctorReviewResponse;
import com.ftn.isa.entity.*;
import com.ftn.isa.repository.*;
import com.ftn.isa.service.IReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewService implements IReviewService {

    private final DoctorReviewRepository _doctor_reviewRepository;

    private final MedicalStaffRepository _medicalStaffRepository;

    private final PatientRepository _patientRepository;

    private final ClinicRepository _clinicRepository;

    private final ClinicReviewRepository _clinicReviewRepository;

    public ReviewService(DoctorReviewRepository doctorReviewRepository, MedicalStaffRepository medicalStaffRepository, PatientRepository patientRepository, ClinicRepository clinicRepository, ClinicReviewRepository clinicReviewRepository) {
        _doctor_reviewRepository = doctorReviewRepository;
        _medicalStaffRepository = medicalStaffRepository;
        _patientRepository = patientRepository;
        _clinicRepository = clinicRepository;
        _clinicReviewRepository = clinicReviewRepository;
    }

    @Override
    public DoctorReviewResponse reviewingDoctor(DoctorReviewRequest request) {
        Patient patient = _patientRepository.findOneById(request.getPatientId());
        patient.getDoctorReviewList().stream().forEach(doctorReview -> {
            if (doctorReview.getMedicalStaff() != null) {
                if(request.getMedicalStaffId() == doctorReview.getMedicalStaff().getId()) {
                    try {
                        throw new Exception("Doctor is already rated");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        DoctorReview doctorReview = new DoctorReview();
        doctorReview.setReview(request.getReview());
        doctorReview.setDescription(request.getDescription());
        doctorReview.setPatient(patient);

        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(request.getMedicalStaffId());

        doctorReview.setMedicalStaff(medicalStaff);

        DoctorReview savedDoctorReview = _doctor_reviewRepository.save(doctorReview);
        return mapReviewToDoctorReviewResponse(savedDoctorReview);
    }

    @Override
    public ClinicReviewResponse reviewingClinic(ClinicReviewRequest request) {
        Patient patient = _patientRepository.findOneById(request.getPatientId());
        patient.getClinicReviewList().stream().forEach(clinicReview -> {
            if (clinicReview.getClinic() != null) {
                if(request.getClinicId() == clinicReview.getClinic().getId()) {
                    try {
                        throw new Exception("Clinic is already rated");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ClinicReview clinicReview = new ClinicReview();
        clinicReview.setReview(request.getReview());
        clinicReview.setDescription(request.getDescription());
        clinicReview.setPatient(patient);

        Clinic clinic = _clinicRepository.findOneById(request.getClinicId());

        clinicReview.setClinic(clinic);

        ClinicReview savedClinicReview = _clinicReviewRepository.save(clinicReview);
        return mapReviewToDoctorReviewResponse(savedClinicReview);
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

    private ClinicReviewResponse mapReviewToDoctorReviewResponse(ClinicReview clinicReview) {
        ClinicReviewResponse clinicReviewResponse = new ClinicReviewResponse();
        clinicReviewResponse.setDescription(clinicReview.getDescription());
        clinicReviewResponse.setReview(clinicReview.getReview());
        clinicReviewResponse.setClinicId(clinicReview.getClinic().getId());
        clinicReviewResponse.setPatientId(clinicReview.getPatient().getId());
        clinicReviewResponse.setId(clinicReview.getId());
        return clinicReviewResponse;
    }
}
