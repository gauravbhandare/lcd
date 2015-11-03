package com.lightningcd.api.rest;


import com.lightningcd.api.exception.DeployApplicationNotFoundException;
import com.lightningcd.api.model.DeployApplication;
import com.lightningcd.api.service.DeployApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@Api(value = "Application", description = "Operations related to an Application")
public class DeployApplicationController {


    private  DeployApplicationService deployApplicationService;
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    @Autowired
    public DeployApplicationController(DeployApplicationService deployApplicationService) {
        this.deployApplicationService = deployApplicationService;
    }


    @RequestMapping(value = "/getApplication", method = GET, produces = JSON)
    @ApiOperation(value="Get All Applications", nickname="GetApplications", response=DeployApplication.class )
    public ResponseEntity<DeployApplication> getApplication(@Valid String applicationName) {
        return ResponseEntity.status(HttpStatus.OK).body(deployApplicationService.get(applicationName));
    }

    @RequestMapping(value = "/getObjectIdfromApplicationName", method = GET, produces = JSON)
    public ResponseEntity<String> getApplicationWithObjectId(@Valid String applicationName) {
        DeployApplication deployApplication = deployApplicationService.get(applicationName);
        return ResponseEntity.status(HttpStatus.OK).body(deployApplication.getId().toString());

    }


    @RequestMapping(value = "/createApplication", method = POST, consumes = JSON, produces = JSON)
    @ApiOperation(value="Create an application", nickname="createApplication", response=String.class )
    public ResponseEntity<String> createApplication(@Valid @RequestBody DeployApplication request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deployApplicationService.create(request.getApplicationName(), request.getEnvironments(), request.getComponent(), request.getProvisioningTypes()));

    }


    @RequestMapping(value = "/updateApplication", method = PUT, consumes = JSON, produces = JSON)
    public ResponseEntity<String> updateApplication(@Valid @RequestBody DeployApplication request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(deployApplicationService.update(request.getId(), request.getApplicationName(), request.getEnvironments(), request.getComponent(), request.getProvisioningTypes()));
        } catch (DeployApplicationNotFoundException de) {
            return ResponseEntity.status(HttpStatus.OK).body("User Does Not Exist, Please choose another username");
        }
    }

    @RequestMapping(value = "/deleteApplication", method = GET, produces = JSON)
    public ResponseEntity<String> deleteApplication(@Valid String applicationName) {
        return ResponseEntity.status(HttpStatus.OK).body(deployApplicationService.delete(applicationName));
}
    }
