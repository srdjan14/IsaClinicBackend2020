package com.ftn.isa.service;

import com.ftn.isa.dto.request.ClinicRequest;
import com.ftn.isa.dto.request.SearchDoctorForExaminationRequest;
import com.ftn.isa.dto.response.ClinicResponse;
import com.ftn.isa.dto.response.ExaminationRequestResponse;
import com.ftn.isa.dto.response.MedicalStaffResponse;
import com.ftn.isa.entity.Clinic;
import com.ftn.isa.entity.ExaminationType;
import com.ftn.isa.entity.MedicalStaff;
import com.ftn.isa.repository.ClinicRepository;
import com.ftn.isa.service.implementation.ClinicService;
import com.ftn.isa.utils.enums.DeletedStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ClinicServiceTest {

    private String CLINIC_NAME = "Klinika 1";
    private Date EXAMINATION_DATE = new Date(12,12,2020);
    private String EXAMINATION_TYPE = "Tip 1";
    private Long CLINIC_ID = 2L;
    private Long DOCTOR_ID = 3L;
    private Long EXAMINATION_ID = 10L;
    private String CLINIC_ADDRESS = "Adresa";

    @Mock
    private ClinicRepository _clinicRepository;

    @InjectMocks
    private ClinicService _clinicService;

    @Mock
    private Clinic clinic;

    @Test
    public void testFindOne() {
        Clinic clinic = new Clinic(CLINIC_NAME, "Adresa 1", "Opis 1");
        clinic.setId(1L);
        Mockito.when(_clinicRepository.getOne(1L)).thenReturn(clinic);
        assertEquals(clinic, _clinicRepository.getOne(1L));
        Mockito.verify(_clinicRepository, Mockito.times(1)).getOne(1L);
        Mockito.verifyNoMoreInteractions(_clinicRepository);
    }

    @Test(expected = NullPointerException.class)
    public void searchClinicsWithFreeDoctors() throws Exception {
        ExaminationType examinationType = new ExaminationType();
        examinationType.setId(5L);
        examinationType.setName(EXAMINATION_TYPE);
        examinationType.setDeletedStatus(DeletedStatus.NOT_DELETED);
        examinationType.setPrice(255L);

        Date date = EXAMINATION_DATE;
        SearchDoctorForExaminationRequest request = new SearchDoctorForExaminationRequest();
        request.setExaminationDate(date);
        request.setExaminationType(examinationType);

        MedicalStaffResponse medicalStaffResponse = new MedicalStaffResponse();
        medicalStaffResponse.setExaminationType(examinationType.getName());
        medicalStaffResponse.setClinicId(CLINIC_ID);
        medicalStaffResponse.setId(DOCTOR_ID);

        List<MedicalStaffResponse> medicalStaffList = new ArrayList<>();
        medicalStaffList.add(medicalStaffResponse);

        ExaminationRequestResponse examinationRequestResponse = new ExaminationRequestResponse();
        examinationRequestResponse.setPrice(examinationType.getPrice());
        examinationRequestResponse.setId(EXAMINATION_ID);
        examinationRequestResponse.setExaminationTypeId(examinationType.getId());
        examinationRequestResponse.setExaminationTypeName(examinationType.getName());
        examinationRequestResponse.setMedicalStaffId(DOCTOR_ID);
        examinationRequestResponse.setExaminationDate(EXAMINATION_DATE);

        List<ExaminationRequestResponse> examinationRequestResponseList = new ArrayList<>();
        examinationRequestResponseList.add(examinationRequestResponse);

        ClinicResponse clinicResponse = new ClinicResponse();
        clinicResponse.setName(CLINIC_NAME);
        clinicResponse.setId(CLINIC_ID);

        List<ClinicResponse> clinics = new ArrayList<>();
        clinics.add(clinicResponse);

        clinics = _clinicService.searchFreeDoctorInClinic(request);

        for(ExaminationRequestResponse e: examinationRequestResponseList) {
            assertEquals(e.getExaminationDate(), EXAMINATION_DATE);
            assertEquals(e.getExaminationDate(), EXAMINATION_DATE);
        }

        for(MedicalStaffResponse m: medicalStaffList) {
            assertEquals(m.getExaminationType(), EXAMINATION_TYPE);
        }

        for(ClinicResponse c: clinics) {
            assertEquals(c.getId(), medicalStaffResponse.getClinicId());
        }

        assertNotEquals(clinics, null);
        assertNotEquals(clinics.size(), 0);
    }

    @Test
    public void searchClinics() {
        ClinicRequest request = new ClinicRequest();
        request.setName(CLINIC_NAME);
        request.setAddress("Adresa");
        request.setDescription("Opis");
        request.setX(1f);
        request.setY(2f);

        List<ClinicResponse> clinicResponses = _clinicService.searchClinic(request);

        assertEquals(clinicResponses, null);
    }

    @Test
    public void createClinic() {
        Clinic clinic = new Clinic();
        clinic.setId(CLINIC_ID);
        clinic.setName(CLINIC_NAME);
        clinic.setAddress(CLINIC_ADDRESS);
        clinic.setDescription("Opis");
        clinic.setX(1f);
        clinic.setY(2f);

    }
}
