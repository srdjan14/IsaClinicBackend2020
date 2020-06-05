package com.ftn.isa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationRoomResponse {

    private String name;

    private Long id;

    private int number;
}
