package com.ftn.isa.service;

import com.ftn.isa.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface ITokenProvider {

    String getUsernameFromToken(String token);

    Date getExpirationDateFromToken(String token);

    boolean validateToken(String token, UserDetails userDetails);

    String generateToken(User user);
}