package com.ftn.isa.service.implementation;

import com.ftn.isa.config.EmailContext;
import com.ftn.isa.entity.User;
import com.ftn.isa.service.IEmailService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class EmailService implements IEmailService {

    private final EmailContext _emailContext;

    public EmailService(EmailContext emailContext) {
        _emailContext = emailContext;
    }

    @Override
    public void sendAcceptedMailToPatient(User user) {
        String to = user.getEmail();
        String subject = "You have been approved!";
        Context context = new Context();
        context.setVariable("username", String.format("Dear, %s %s", user.getFirstName(), user.getLastName()));
        _emailContext.send(to, subject, "approvePatient", context);
    }

    @Override
    public void sendDeniedMailToPatient(User user) {
        String to = user.getEmail();
        String subject = "You have been denied!";
        Context context = new Context();
        context.setVariable("username", String.format("Dear, %s %s", user.getFirstName(), user.getLastName()));
        _emailContext.send(to, subject, "declinePatient", context);
    }

    @Override
    public void sendAcceptedVacation(User user) {
        String to = user.getEmail();
        String subject = "Your vacation been approved!";
        Context context = new Context();
        context.setVariable("username", String.format("Dear, %s %s", user.getFirstName(), user.getLastName()));
        _emailContext.send(to, subject, "approveVacation", context);
    }

    @Override
    public void sendDeniedVacation(User user) {
        String to = user.getEmail();
        String subject = "You have been denied!";
        Context context = new Context();
        context.setVariable("username", String.format("Dear, %s %s", user.getFirstName(), user.getLastName()));
        _emailContext.send(to, subject, "declineVacation", context);
    }

    @Override
    public void sendConfirmedExamination(User user) {
        String to = user.getEmail();
        String subject = "Your examination has been confirmed!";
        Context context = new Context();
        context.setVariable("username", String.format("Dear, %s %s", user.getFirstName(), user.getLastName()));
        _emailContext.send(to, subject, "confirmationExamination", context);
    }
}
