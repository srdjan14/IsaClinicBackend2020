package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.CreatePatientRequest;
import com.ftn.isa.dto.response.RegistartionRequestResponse;
import com.ftn.isa.entity.Patient;
import com.ftn.isa.entity.QRegistrationRequest;
import com.ftn.isa.entity.RegistrationRequest;
import com.ftn.isa.repository.RegistrationRequestRepository;
import com.ftn.isa.repository.PatientRepository;
import com.ftn.isa.service.IEmailService;
import com.ftn.isa.service.IRegistrationRequestService;
import com.ftn.isa.utils.enums.RequestStatus;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationRequestService implements IRegistrationRequestService {
    
    private final RegistrationRequestRepository _registrationRequestRepository;

    private final PatientRepository _patientRepository;

    private final IEmailService _emailService;


    public RegistrationRequestService(RegistrationRequestRepository registrationRequestRepository, PatientRepository patientRepository, IEmailService emailService) {
        _registrationRequestRepository = registrationRequestRepository;
        _patientRepository = patientRepository;
        _emailService = emailService;
    }
    
    @Override
    public List<RegistartionRequestResponse> getAll() {
        QRegistrationRequest qRegistrationRequest = QRegistrationRequest.registrationRequest;
        JPAQuery query = _registrationRequestRepository.getQuery();

        query.select(qRegistrationRequest).where(qRegistrationRequest.status.eq(RequestStatus.PENDING));
        List<RegistrationRequest> list = query.fetch();

        return list
                .stream()
                .map(request -> mapToRegistrationResponse(request))
                .collect(Collectors.toList());
    }

    @Override
    public void approveRegistrationRequest(Long id) throws Exception {
        RegistrationRequest request = _registrationRequestRepository.findOneById(id);

        if (request == null) {
            throw new Exception(String.format("Request with %s id not found", id.toString()));
        }

        request.setStatus(RequestStatus.APPROVED);
        _registrationRequestRepository.save(request);

        Patient patient = _patientRepository.findOneByUser_Email(request.getEmail());
        patient.setActive(true);
        _patientRepository.save(patient);

        _emailService.sendAcceptedMailToPatient(patient.getUser());

    }

    @Override
    public void declineRegistrationRequest(Long id) throws Exception {
        RegistrationRequest request = _registrationRequestRepository.findOneById(id);

        if (request == null) {
            throw new Exception(String.format("Request with %s id not found", id.toString()));
        }

        request.setStatus(RequestStatus.DENIED);
        _registrationRequestRepository.save(request);

        Patient patient = _patientRepository.findOneByUser_Email(request.getEmail());
        patient.setActive(false);
        _patientRepository.save(patient);

        _emailService.sendDeniedMailToPatient(patient.getUser());

    }

    @Override
    public void createRegistrationRequest(CreatePatientRequest request) {
        RegistrationRequest registrationRequest = mapToRegistrationRequest(request);

        _registrationRequestRepository.save(registrationRequest);
    }

    private RegistrationRequest mapToRegistrationRequest(CreatePatientRequest request) {
        RegistrationRequest result = new RegistrationRequest();
        result.setStatus(RequestStatus.PENDING);
        result.setAddress(request.getAddress());
        result.setCity(request.getCity());
        result.setCountry(request.getCountry());
        result.setEmail(request.getEmail());
        result.setFirstName(request.getFirstName());
        result.setLastName(request.getLastName());
        result.setPhone(request.getPhone());
        result.setSsn(request.getSsn());
        result.setPassword("********");
        return result;
    }


    private RegistartionRequestResponse mapToRegistrationResponse(RegistrationRequest registrationRequest) {
        RegistartionRequestResponse result = new RegistartionRequestResponse();
        result.setEmail(registrationRequest.getEmail());
        result.setFirstName(registrationRequest.getFirstName());
        result.setLastName(registrationRequest.getLastName());
        result.setCountry(registrationRequest.getCountry());
        result.setCity(registrationRequest.getCity());
        result.setAddress(registrationRequest.getAddress());
        result.setPhone(registrationRequest.getPhone());
        result.setSsn(registrationRequest.getSsn());
        result.setStatus(registrationRequest.getStatus());
        result.setId(registrationRequest.getId());
        return result;
    }
}
