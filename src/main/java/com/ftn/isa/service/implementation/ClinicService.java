package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.ClinicRequest;
import com.ftn.isa.dto.response.ClinicResponse;
import com.ftn.isa.entity.Clinic;
import com.ftn.isa.repository.ClinicRepository;
import com.ftn.isa.service.IClinicService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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

        Clinic savedClinic = _clinicRepository.save(clinic);
        return mapClinicToClinicResponse(savedClinic);
    }

    @Override
    public ClinicResponse updateClinic(Long id, ClinicRequest request) throws Exception {
        return null;
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

    private ClinicResponse mapClinicToClinicResponse(Clinic clinic) {
        ClinicResponse clinicResponse = new ClinicResponse();
        clinicResponse.setAddress(clinic.getAddress());
        clinicResponse.setDescription(clinic.getDescription());
        clinicResponse.setName(clinic.getName());
        clinicResponse.setId(clinic.getId());
        return clinicResponse;
    }
}
