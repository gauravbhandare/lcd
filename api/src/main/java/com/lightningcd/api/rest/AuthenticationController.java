package com.lightningcd.api.rest;


import com.lightningcd.api.exception.LoginFailedException;
import com.lightningcd.api.exception.UserNotFoundException;
import com.lightningcd.api.model.Authentication;
import com.lightningcd.api.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@Api(value = "AuthenticationApi", description = "API for lcd authentication")
public class AuthenticationController {

    //private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private AuthenticationService authenticationService;
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/authenticateUser", method = RequestMethod.POST, consumes = JSON, produces = JSON)
    @ApiOperation(value = "AuthenticateUser", nickname = "Authenticate", response = Authentication.class)
    public ResponseEntity<Authentication> authenticateUser(@Valid @RequestBody Authentication request) throws UserNotFoundException, LoginFailedException {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticateUser(request.getUsername(), request.getPassword()));
    }


    @RequestMapping(value = "/getUser", method = RequestMethod.GET, produces = JSON)
    @ApiOperation(value = "Get User", nickname = "GetUser", response = Authentication.class)
    public ResponseEntity<Authentication> getUser(@Valid String username) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.get(username));
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = JSON, produces = JSON)
    @ApiOperation(value = "Create User", nickname = "CreateUser", response = Authentication.class)
    public ResponseEntity<Authentication> createUser(@Valid @RequestBody Authentication request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authenticationService.create(request.getUsername(), request.getPassword()));
        } catch (org.springframework.dao.DuplicateKeyException ue) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Authentication("User Already Exist", ""));
        }
    }


    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, consumes = JSON, produces = JSON)
    @ApiOperation(value = "Update User", nickname = "UpdateUser", response = Authentication.class)
    public ResponseEntity<Authentication> updateUser(@Valid @RequestBody Authentication request) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.update(request.getUsername(), request.getPassword()));

    }


    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE, produces = JSON)
    @ApiOperation(value = "Delete User", nickname = "DeleteUser", response = String.class)
    public ResponseEntity<String> deleteUser(@Valid String username) throws UserNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authenticationService.delete(username));
        } catch (UserNotFoundException ue) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
        }
    }


    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User does not exist")
    void handleBadRequests(HttpServletResponse response) throws IOException {
        //Intentionally left empty
    }

    @ExceptionHandler({LoginFailedException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid Username or Password")
    void handleInvalidLoginRequests(HttpServletResponse response) throws IOException {
        //Intentionally left empty
    }


}
