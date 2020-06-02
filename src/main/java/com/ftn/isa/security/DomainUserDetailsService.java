package com.ftn.isa.security;

import com.ftn.isa.entity.User;
import com.ftn.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component("SecurityService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository _userRepository;

    @Autowired
    public DomainUserDetailsService(UserRepository userRepository) {
        _userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String email) {
        List<User> users = _userRepository.findAll();
        _userRepository.findOneByEmail(email);

        Optional<User> userByUsernameFromDatabase = Optional.of(_userRepository.findOneByEmail(email));
        userByUsernameFromDatabase.get();
        return userByUsernameFromDatabase.map(user -> createSpringSecurityUser(user)).orElseThrow(() ->
                new UsernameNotFoundException("User " + email + " was not found in the " +
                        "database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
//        if (!user.get())
//            throw new UserNotActivated(user.getEmail());

        List<GrantedAuthority> grantedAuthorities = Collections
                .singletonList((new SimpleGrantedAuthority(user.getUserType().name())));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                grantedAuthorities);
    }
}