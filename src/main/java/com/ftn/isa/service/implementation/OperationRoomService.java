package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.OperationRoomRequest;
import com.ftn.isa.dto.request.SearchOperationRoomRequest;
import com.ftn.isa.dto.response.OperationRoomResponse;
import com.ftn.isa.entity.*;
import com.ftn.isa.repository.ClinicRepository;
import com.ftn.isa.repository.ExaminationRequestRepository;
import com.ftn.isa.repository.OperationRoomRepository;
import com.ftn.isa.service.IOperationRoomService;
import com.ftn.isa.utils.enums.DeletedStatus;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationRoomService implements IOperationRoomService {

    private final ClinicRepository _clinicRepository;

    private final OperationRoomRepository _operationRoomRepository;

    private final ExaminationRequestRepository _examinationRequestRepository;

    public OperationRoomService(ClinicRepository clinicRepository, OperationRoomRepository operationRoomRepository, ExaminationRequestRepository examinationRequestRepository) {
        _clinicRepository = clinicRepository;
        _operationRoomRepository = operationRoomRepository;
        _examinationRequestRepository = examinationRequestRepository;
    }

    @Override
    public OperationRoomResponse createOperationRoom(OperationRoomRequest request, Long clinicId) {
        OperationRoom operationRoom = new OperationRoom();

        Clinic clinic = _clinicRepository.findOneById(clinicId);
        operationRoom.setClinic(clinic);
        operationRoom.setName(request.getName());
        operationRoom.setNumber(request.getNumber());
        operationRoom.setDeletedStatus(DeletedStatus.NOT_DELETED);

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

        QOperationRoom qOperationRoom = QOperationRoom.operationRoom;
        JPAQuery query = _operationRoomRepository.getQuery();

        query.select(qOperationRoom).where(qOperationRoom.clinic.id.eq(id)).distinct();
        query.where(qOperationRoom.deletedStatus.eq(DeletedStatus.NOT_DELETED));

        List<OperationRoom> list = query.fetch();

        return list
                .stream()
                .map(operationRoom -> mapOperationRoomToOperationRoomResponse(operationRoom))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOperationRoom(Long id) throws Exception {
        OperationRoom operationRoom = _operationRoomRepository.findOneById(id);

        QOperationRoom qOperationRoom = QOperationRoom.operationRoom;
        QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
        JPAQuery query = _operationRoomRepository.getQuery();

        query.select(qOperationRoom).leftJoin(qExaminationRequest).on(qOperationRoom.id.eq(qExaminationRequest.operationRoom.id)).where(qExaminationRequest.operationRoom.id.isNotNull()).distinct();
        query.where(qOperationRoom.id.eq(id));
        List<OperationRoom> list = query.fetch();
        if(!list.isEmpty()) {
            throw new Exception("This operation room is booked for examination");
        }

        operationRoom.setDeletedStatus(DeletedStatus.IS_DELETED);
        _operationRoomRepository.save(operationRoom);
    }

    @Override
    public List<OperationRoomResponse> searchOperationRoom(SearchOperationRoomRequest request, Long clinicId, Long examinationRequestId) throws Exception {
        ExaminationRequest examinationRequest = _examinationRequestRepository.findOneById(examinationRequestId);

        QOperationRoom qOperationRoom = QOperationRoom.operationRoom;
        QExaminationRequest qExaminationRequest = QExaminationRequest.examinationRequest;
        JPAQuery query = _operationRoomRepository.getQuery();

        query.select(qOperationRoom).where(qOperationRoom.clinic.id.eq(clinicId)).distinct();
//        query.leftJoin(qExaminationRequest).on(qOperationRoom.id.eq(qExaminationRequest.operationRoom.id)).where(qExaminationRequest.operationRoom.id.isNotNull());
//        query.where(qExaminationRequest.examinationDate.eq(examinationRequest.getExaminationDate()));
//        query.where(qExaminationRequest.startAt.after(examinationRequest.getStartAt()));
//        query.where(qExaminationRequest.endAt.before(examinationRequest.getEndAt()));

//        List<OperationRoom> list = query.fetch();
//
//        if(!list.isEmpty()) {
//            throw new Exception("There are no available examination rooms for that time, choose different time");
//        }

        if(request.getName() != null) {
            query.where(qOperationRoom.name.containsIgnoreCase(request.getName()));
        }

        if(request.getNumber() != 0) {
            query.where(qOperationRoom.number.in(request.getNumber()));
        }

        List<OperationRoom> list1 = query.fetch();
        return list1
                .stream()
                .map(operationRoom -> mapOperationRoomToOperationRoomResponse(operationRoom))
                .collect(Collectors.toList());

    }

    public OperationRoomResponse mapOperationRoomToOperationRoomResponse(OperationRoom operationRoom) {
        OperationRoomResponse operationRoomResponse = new OperationRoomResponse();
        operationRoomResponse.setName(operationRoom.getName());
        operationRoomResponse.setNumber(operationRoom.getNumber());
        operationRoomResponse.setId(operationRoom.getId());
        operationRoomResponse.setDeletedStatus(operationRoom.getDeletedStatus());

        return operationRoomResponse;
    }

}
