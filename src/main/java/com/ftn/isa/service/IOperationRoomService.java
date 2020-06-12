package com.ftn.isa.service;

import com.ftn.isa.dto.request.OperationRoomRequest;
import com.ftn.isa.dto.response.OperationRoomResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IOperationRoomService {

    OperationRoomResponse createOperationRoom(OperationRoomRequest request, Long clinicId);

    OperationRoomResponse updateOperationRoom(OperationRoomRequest operationRoomRequest, Long id);

    List<OperationRoomResponse> getOperationRooms();

    OperationRoomResponse getOperationRoom(Long id) throws Exception;

    List<OperationRoomResponse> getAllOperationRoomsByClinic(Long id) throws Exception;

    void deleteOperationRoom(Long id) throws Exception;
}
