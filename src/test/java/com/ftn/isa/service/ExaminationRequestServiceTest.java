package com.ftn.isa.service;

import com.ftn.isa.TestBase;
import com.ftn.isa.repository.*;

import com.ftn.isa.service.implementation.ExaminationRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class ExaminationRequestServiceTest extends TestBase<IExaminationRequestService> {

    protected final ClinicRepository _clinicRepository = Mockito.mock(ClinicRepository.class);

    protected final MedicalStaffRepository _medicalStaffRepository = Mockito.mock(MedicalStaffRepository.class);

    protected final ExaminationRequestRepository _examinationRequestRepository = Mockito.mock(ExaminationRequestRepository.class);

    protected final ExaminationTypeRepository _examinationTypeRepository = Mockito.mock(ExaminationTypeRepository.class);

    protected final PatientRepository _patientRepository = Mockito.mock(PatientRepository.class);

    protected final OperationRoomRepository _operationRoomRepository = Mockito.mock(OperationRoomRepository.class);

    protected final IEmailService _emailService = Mockito.mock(IEmailService.class);

    protected final VacationRequestRepository _vacationRequestRepository = Mockito.mock(VacationRequestRepository.class);

    @Override
    @BeforeEach
    protected void init() {
        service = new ExaminationRequestService(_clinicRepository, _medicalStaffRepository, _examinationRequestRepository,
                _examinationTypeRepository, _patientRepository, _operationRoomRepository, _emailService, _vacationRequestRepository);
    }
}
