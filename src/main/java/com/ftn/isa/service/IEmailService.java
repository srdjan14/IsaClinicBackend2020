package com.ftn.isa.service;

import com.ftn.isa.entity.User;

import java.util.List;

public interface IEmailService {

    void sendAcceptedMailToPatient(User user);

    void sendDeniedMailToPatient(User user);
}
