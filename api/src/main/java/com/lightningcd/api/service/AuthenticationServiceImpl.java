package com.lightningcd.api.service;


import com.lightningcd.api.exception.LoginFailedException;
import com.lightningcd.api.exception.UserNotFoundException;
import com.lightningcd.api.model.Authentication;
import com.lightningcd.api.repository.AuthenticationRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationRepository authenticationRepository;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public Iterable<Authentication> all() {
        return authenticationRepository.findAll();
    }

    @Override
    public Authentication get(ObjectId id) {
        return authenticationRepository.findOne(id);
    }

    @Override
    public Authentication get(String username) throws UserNotFoundException {
        Authentication authentication = authenticationRepository.findByUsername(username);
        if (null != authentication) {
            return authentication;
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }

    @Override
    public Authentication create(String username, String password) {
        Authentication authentication = new Authentication(username, password);
        authenticationRepository.save(authentication);
        return authentication;
    }

    @Override
    public Authentication update(String username, String password) throws UserNotFoundException {
        Authentication authentication = authenticationRepository.findByUsername(username);
        if (null != authentication) {
            authentication.setPassword(password);
            authentication.setUsername(username);
            authenticationRepository.save(authentication);
            return authentication;
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }

    @Override
    public String delete(String username) throws UserNotFoundException {
        Authentication authentication = authenticationRepository.findByUsername(username);
        if (null != authentication) {
            authenticationRepository.delete(authentication);
            return authentication.getUsername();
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }

    @Override
    public Authentication authenticateUser(String username, String password) throws UserNotFoundException, LoginFailedException {
        Authentication authentication = authenticationRepository.findByUsername(username);
        if (null != authentication) {
            if (authentication.getPassword().equals(password)) {
                return authentication;
            } else {
                throw new LoginFailedException("Invalid Username or Password");
            }
        } else {
            throw new UserNotFoundException("User does not exist");
        }
    }
}
