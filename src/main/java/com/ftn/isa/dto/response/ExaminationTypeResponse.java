package com.ftn.isa.dto.response;

import com.ftn.isa.utils.enums.DeletedStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationTypeResponse {

    private String name;

    private float price;

    private Long id;

    private DeletedStatus deletedStatus;
}
