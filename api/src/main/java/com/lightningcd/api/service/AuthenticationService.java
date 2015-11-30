package com.lightningcd.api.service;


import com.lightningcd.api.exception.LoginFailedException;
import com.lightningcd.api.exception.UserNotFoundException;
import com.lightningcd.api.model.Authentication;
import org.bson.types.ObjectId;

public interface AuthenticationService {

    Iterable<Authentication> all();

    Authentication get(ObjectId id);

    Authentication get(String username) throws UserNotFoundException;

    Authentication create(String username, String password);

    Authentication update(String username, String password) throws UserNotFoundException;

    String delete(String username) throws UserNotFoundException;

    Authentication authenticateUser(String username, String password) throws UserNotFoundException, LoginFailedException;

}
