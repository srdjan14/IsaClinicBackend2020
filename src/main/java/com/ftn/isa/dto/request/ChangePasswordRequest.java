package com.ftn.isa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangePasswordRequest {

    private String password;

    private String rePassword;
}
