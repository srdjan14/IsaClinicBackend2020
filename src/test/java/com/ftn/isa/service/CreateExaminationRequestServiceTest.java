package com.ftn.isa.service;

import com.ftn.isa.dto.request.CreateExaminationRequest;
import com.ftn.isa.entity.*;
import com.ftn.isa.utils.enums.DeletedStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateExaminationRequestServiceTest extends ExaminationRequestServiceTest {

    private Random random = new Random();
    private Long CLINIC_ID = random.nextLong();
    private Long EXAMINATION_REQUEST_ID = random.nextLong();
    private Long MEDICAL_STAFF_ID = random.nextLong();
    private Long PATIENT_ID = random.nextLong();
    private Long EXAMINATION_TYPE = random.nextLong();
    private LocalTime LOCAL_TIME = LocalTime.of(12,12,12);

//    @Test
//    public void shouldCallPatientRepository_WhenMethodIsCalled() throws Exception {
//        Mockito.doReturn(getPatient()).when(_patientRepository).findOneById(any(Long.class));
//        doReturn(getClinic()).when(_clinicRepository).findOneById(any(Long.class));
//        doReturn(getExaminationRequest()).when(_examinationRequestRepository).findOneById(any(Long.class));
//        doReturn(getExaminationType()).when(_examinationTypeRepository).findOneById(any(Long.class));
//        doReturn(getMedicalStaff()).when(_medicalStaffRepository).findOneById(any(Long.class));
//        CreateExaminationRequest createExaminationRequest = createRequest();
//
//        service.createPredefinedExaminationRequest(createExaminationRequest);
//
//        verify(_patientRepository, times(1)).findOneById(any(Long.class));
//    }
//
//    @Test
//    public void shouldCallMedicalStaffRepository_WhenMethodIsCalled() throws Exception {
//        Mockito.doReturn(getPatient()).when(_patientRepository).findOneById(any(Long.class));
//        doReturn(getClinic()).when(_clinicRepository).findOneById(any(Long.class));
//        doReturn(getExaminationRequest()).when(_examinationRequestRepository).findOneById(any(Long.class));
//        doReturn(getExaminationType()).when(_examinationTypeRepository).findOneById(any(Long.class));
//        doReturn(getMedicalStaff()).when(_medicalStaffRepository).findOneById(any(Long.class));
//        CreateExaminationRequest createExaminationRequest = createRequest();
//
//        service.createPredefinedExaminationRequest(createExaminationRequest);
//
//        verify(_patientRepository, times(1)).findOneById(any(Long.class));
//    }

    private Patient getPatient() {
        Patient patient = new Patient();
        patient.setId(random.nextLong());

        return patient;
    }

    private ExaminationType getExaminationType() {
        ExaminationType examinationType = new ExaminationType();
        examinationType.setId(random.nextLong());

        return examinationType;
    }

    private Clinic getClinic() {
        Clinic clinic = new Clinic();
        clinic.setId(random.nextLong());

        return clinic;
    }

    private MedicalStaff getMedicalStaff() {
        MedicalStaff medicalStaff = new MedicalStaff();
        medicalStaff.setId(random.nextLong());

        return medicalStaff;
    }

    private OperationRoom getOperationRoom() {
        OperationRoom operationRoom = new OperationRoom();
        operationRoom.setId(random.nextLong());

        return operationRoom;
    }

    private ExaminationRequest getExaminationRequest() {
        ExaminationRequest examinationRequest = new ExaminationRequest();
        examinationRequest.setId(random.nextLong());

        Patient patient = new Patient();
        patient.setId(random.nextLong());
        examinationRequest.setPatient(patient);

        return examinationRequest;
    }

    private CreateExaminationRequest createRequest() {
        ExaminationType examinationType = new ExaminationType();
        examinationType.setPrice(250);
        examinationType.setDeletedStatus(DeletedStatus.NOT_DELETED);
        examinationType.setName("Ortopedija");
        examinationType.setId(random.nextLong());

        CreateExaminationRequest createExaminationRequest = new CreateExaminationRequest();
        createExaminationRequest.setExaminationDate(new Date());
        createExaminationRequest.setClinicId(CLINIC_ID);
        createExaminationRequest.setDoctorId(MEDICAL_STAFF_ID);
        createExaminationRequest.setExaminationTypeId(EXAMINATION_TYPE);
        createExaminationRequest.setStartAt(LOCAL_TIME);

        return createExaminationRequest;
    }

    private void createMocks() {
        when(_patientRepository.findOneById(any(Long.class))).thenReturn(getPatient());
        when(_clinicRepository.findOneById(any(Long.class))).thenReturn(getClinic());
        when(_examinationRequestRepository.findOneById(any(Long.class))).thenReturn(getExaminationRequest());
        when(_examinationTypeRepository.findOneById(any(Long.class))).thenReturn(getExaminationType());
        when(_medicalStaffRepository.findOneById(any(Long.class))).thenReturn(getMedicalStaff());
    }
}
