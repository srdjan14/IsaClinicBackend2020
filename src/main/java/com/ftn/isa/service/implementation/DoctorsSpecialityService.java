package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.DoctorsSpecialityRequest;
import com.ftn.isa.dto.response.DoctorsSpecialityResponse;
import com.ftn.isa.entity.DoctorsSpeciality;
import com.ftn.isa.repository.DoctorsSpecialityRepository;
import com.ftn.isa.service.IDoctorsSpecialityService;
import org.springframework.stereotype.Service;

@Service
public class DoctorsSpecialityService implements IDoctorsSpecialityService {

    private final DoctorsSpecialityRepository _doctorsSpecialityRepository;

    public DoctorsSpecialityService(DoctorsSpecialityRepository doctorsSpecialityRepository) {
        _doctorsSpecialityRepository = doctorsSpecialityRepository;
    }

    @Override
    public DoctorsSpecialityResponse createSpeciality(DoctorsSpecialityRequest request) throws Exception {
        DoctorsSpeciality speciality = new DoctorsSpeciality();
        speciality.setPrice(request.getPrice());
        speciality.setSpeciality(request.getSpeciality());

        DoctorsSpeciality savedSpeciality = _doctorsSpecialityRepository.save(speciality);
        return mapDoctorsSpecialityToDoctorsSpecialityResponse(savedSpeciality);
    }

    @Override
    public DoctorsSpecialityResponse updateSpeciality(DoctorsSpecialityRequest request, Long id) throws Exception {
        DoctorsSpeciality speciality = _doctorsSpecialityRepository.findOneById(id);
        speciality.setPrice(request.getPrice());
        speciality.setSpeciality(request.getSpeciality());

        DoctorsSpeciality savedSpeciality = _doctorsSpecialityRepository.save(speciality);
        return mapDoctorsSpecialityToDoctorsSpecialityResponse(savedSpeciality);
    }

    @Override
    public DoctorsSpecialityResponse getSpeciality(Long id) throws Exception {
        DoctorsSpeciality speciality = _doctorsSpecialityRepository.findOneById(id);

        if (speciality == null) {
            throw new Exception(String.format("Speciality with %s id is not found", id));
        }

        return mapDoctorsSpecialityToDoctorsSpecialityResponse(speciality);
    }

    public DoctorsSpecialityResponse mapDoctorsSpecialityToDoctorsSpecialityResponse(DoctorsSpeciality doctorsSpeciality){
        DoctorsSpecialityResponse doctorsSpecialityResponse = new DoctorsSpecialityResponse();
        doctorsSpecialityResponse.setId(doctorsSpeciality.getId());
        doctorsSpecialityResponse.setSpeciality(doctorsSpeciality.getSpeciality());
        doctorsSpecialityResponse.setPrice(doctorsSpeciality.getPrice());
        return doctorsSpecialityResponse;
    }
}
