package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.OperationRoomRequest;
import com.ftn.isa.dto.response.OperationRoomResponse;
import com.ftn.isa.entity.Clinic;
import com.ftn.isa.entity.OperationRoom;
import com.ftn.isa.repository.ClinicRepository;
import com.ftn.isa.repository.OperationRoomRepository;
import com.ftn.isa.service.IOperationRoomService;
import com.ftn.isa.utils.enums.DeletedStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationRoomService implements IOperationRoomService {

    private final ClinicRepository _clinicRepository;

    private final OperationRoomRepository _operationRoomRepository;

    public OperationRoomService(ClinicRepository clinicRepository, OperationRoomRepository operationRoomRepository) {
        _clinicRepository = clinicRepository;
        _operationRoomRepository = operationRoomRepository;
    }

    @Override
    public OperationRoomResponse createOperationRoom(OperationRoomRequest request, Long clinicId) {
        OperationRoom operationRoom = new OperationRoom();

        Clinic clinic = _clinicRepository.findOneById(clinicId);
        operationRoom.setClinic(clinic);
        operationRoom.setName(request.getName());
        operationRoom.setNumber(request.getNumber());

        OperationRoom savedOperationRoom = _operationRoomRepository.save(operationRoom);

        return mapOperationRoomToOperationRoomResponse(savedOperationRoom);
    }

    @Override
    public OperationRoomResponse updateOperationRoom(OperationRoomRequest operationRoomRequest, Long id) {
        OperationRoom operationRoom = _operationRoomRepository.findOneById(id);

        operationRoom.setNumber(operationRoomRequest.getNumber());
        operationRoom.setName(operationRoomRequest.getName());

        OperationRoom savedOperationRoom = _operationRoomRepository.save(operationRoom);

        return mapOperationRoomToOperationRoomResponse(savedOperationRoom);
    }

    @Override
    public List<OperationRoomResponse> getOperationRooms() {
        List<OperationRoom> operationRooms = _operationRoomRepository.findAll();

        return operationRooms
                .stream()
                .map(operationRoom -> mapOperationRoomToOperationRoomResponse(operationRoom))
                .collect(Collectors.toList());    }

    @Override
    public OperationRoomResponse getOperationRoom(Long id) throws Exception {
        OperationRoom operationRoom = _operationRoomRepository.findOneById(id);

        if (operationRoom == null) {
            throw new Exception("Operation Room isn't found");
        }

        return mapOperationRoomToOperationRoomResponse(operationRoom);
    }

    @Override
    public List<OperationRoomResponse> getAllOperationRoomsByClinic(Long id) throws Exception {
        Clinic clinic = _clinicRepository.findOneById(id);
        if (clinic == null) {
            throw new Exception(String.format("Clinic with % id not found", id.toString()));
        }

        List<OperationRoom> operationRooms = _operationRoomRepository.findAllByClinic_Id(clinic.getId());

        return operationRooms
                .stream()
                .map(operationRoom -> mapOperationRoomToOperationRoomResponse(operationRoom))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOperationRoom(Long id) {
        OperationRoom operationRoom = _operationRoomRepository.findOneById(id);
        operationRoom.setDeletedStatus(DeletedStatus.IS_DELETED);
        _operationRoomRepository.save(operationRoom);
    }

    public OperationRoomResponse mapOperationRoomToOperationRoomResponse(OperationRoom operationRoom) {
        OperationRoomResponse operationRoomResponse = new OperationRoomResponse();
        operationRoomResponse.setName(operationRoom.getName());
        operationRoomResponse.setNumber(operationRoom.getNumber());
        operationRoomResponse.setId(operationRoom.getId());

        return operationRoomResponse;
    }

}
