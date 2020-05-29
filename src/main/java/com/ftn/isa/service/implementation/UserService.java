package com.ftn.isa.service.implementation;

import com.ftn.isa.dto.request.CreateUserRequest;
import com.ftn.isa.dto.response.UserResponse;
import com.ftn.isa.entity.User;
import com.ftn.isa.repository.UserRepository;
import com.ftn.isa.service.IUserService;
import com.ftn.isa.utils.enums.DeletedStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final PasswordEncoder _passwordEncoder;

    private final UserRepository _userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        _passwordEncoder = passwordEncoder;
        _userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) throws Exception {
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new Exception("Password must match");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCountry(request.getCountry());
        user.setCity(request.getCity());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setSsn(request.getSsn());
        user.setUserType(request.getUserType());
        user.setDeletedStatus(DeletedStatus.NOT_DELETED);

        user.setPassword(_passwordEncoder.encode(request.getPassword()));

        User savedUser = _userRepository.save(user);

        return mapUserToUserResponse(savedUser);
    }

    private UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setId(user.getId());
        userResponse.setAddress(user.getAddress());
        userResponse.setCity(user.getCity());
        userResponse.setCountry(user.getCountry());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhone(user.getPhone());
        userResponse.setSsn(user.getSsn());
        userResponse.setUserType(user.getUserType());
        userResponse.setDeletedStatus(user.getDeletedStatus());
        return userResponse;
    }
}
