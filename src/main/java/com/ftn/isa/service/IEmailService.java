package com.ftn.isa.service;

import com.ftn.isa.entity.User;

import java.util.List;

public interface IEmailService {

    void sendAcceptedMailToPatient(User user);

    void sendDeniedMailToPatient(User user);

    void sendAcceptedVacation(User user);

    void sendDeniedVacation(User user);

    void sendConfirmedExamination(User user);

    void sendDeniedExamination(User user);
}
