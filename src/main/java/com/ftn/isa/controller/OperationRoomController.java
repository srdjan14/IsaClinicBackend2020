package com.ftn.isa.controller;

import com.ftn.isa.dto.request.OperationRoomRequest;
import com.ftn.isa.dto.request.SearchOperationRoomRequest;
import com.ftn.isa.dto.response.OperationRoomResponse;
import com.ftn.isa.service.IOperationRoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation-room")
public class OperationRoomController {

    private final IOperationRoomService _operationRoomService;

    public OperationRoomController(IOperationRoomService operationRoomService) {
        _operationRoomService = operationRoomService;
    }

    @PostMapping("/create-operation-room/{id}")
    public void createOperationRoom(@RequestBody OperationRoomRequest operationRoomRequest, @PathVariable Long id) {
        _operationRoomService.createOperationRoom(operationRoomRequest, id);
    }

    @PutMapping("update-operation-room/{id}")
    public void updateOperationRoom(@RequestBody OperationRoomRequest operationRoomRequest, @PathVariable Long id) {
        _operationRoomService.updateOperationRoom(operationRoomRequest, id);
    }

    @GetMapping
    public List<OperationRoomResponse> getOperationRooms() {
        return _operationRoomService.getOperationRooms();
    }

    @GetMapping("/{id}")
    public OperationRoomResponse getOperationRoom(@PathVariable Long id) throws Exception {
        return _operationRoomService.getOperationRoom(id);
    }

    @GetMapping("/{id}/room")
    public List<OperationRoomResponse> getAllOperationRoomsByClinic(@PathVariable Long id) throws Exception {
        return _operationRoomService.getAllOperationRoomsByClinic(id);
    }

    @PutMapping("/delete/{id}")
    public void deleteOperationRoom(@PathVariable Long id) throws Exception {
        _operationRoomService.deleteOperationRoom(id);
    }

    @GetMapping("/{clinicId}/{examinationId}/search")
    public List<OperationRoomResponse> searchOperationRooms(SearchOperationRoomRequest request, @PathVariable Long clinicId, @PathVariable Long examinationId) throws Exception {
        return _operationRoomService.searchOperationRoom(request, clinicId, examinationId);
    }

    @GetMapping("/{clinicId}/search")
    public List<OperationRoomResponse> searchOperationRoomsByAdmin(SearchOperationRoomRequest request, @PathVariable Long clinicId) throws Exception {
        return _operationRoomService.searchOperationRoomsByAdmin(request, clinicId);
    }
}
