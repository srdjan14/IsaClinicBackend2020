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
        _emailContext.send(to, subject, "deniedPatient", context);
    }

}