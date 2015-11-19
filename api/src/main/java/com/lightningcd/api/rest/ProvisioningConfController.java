package com.lightningcd.api.rest;


import com.lightningcd.api.exception.ConfigurationNotFoundException;
import com.lightningcd.api.model.ProvisioningConf;
import com.lightningcd.api.service.ProvisioningConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Api(value = "Provisioning", description = "Operations related to configure application Provisioning")
public class ProvisioningConfController {


    private ProvisioningConfService provisioiningConfService;
    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    @Autowired
    public ProvisioningConfController(ProvisioningConfService provisioiningConfService) {
        this.provisioiningConfService = provisioiningConfService;
    }

    @RequestMapping(value = "/getProvisioningConf", method = GET, produces = JSON)
    @ApiOperation(value = "Get All ProvisioningConf", nickname = "GetProvisioningConf", response = ProvisioningConf.class)
    public ResponseEntity<ProvisioningConf> getProvisioningConf(@Valid String applicationName) {
        return ResponseEntity.status(HttpStatus.OK).body((provisioiningConfService.get(applicationName)));
    }

    @RequestMapping(value = "/createProvisioningConf", method = POST, consumes = JSON, produces = JSON)
    @ApiOperation(value = "Create Provisioning Configuration", nickname = "CreateProvisioningConf", response = String.class)
    public ResponseEntity<String> createProvisioningConf(@Valid @RequestBody ProvisioningConf request) {
        return ResponseEntity.status(HttpStatus.OK).body(provisioiningConfService.create(request));
    }

    @RequestMapping(value = "/updateProvisioningConf", method = POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Provisioning Configuration", nickname = "UpdateProvisioningConf", response = String.class)
    public ResponseEntity<String> updateProvisioningConf(@Valid @RequestBody ProvisioningConf request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(provisioiningConfService.update(request));
        } catch (ConfigurationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body("Configuration Does Not Exist, Please check");
        }
    }

    @RequestMapping(value = "/deleteProvisioningConf", method = DELETE, produces = JSON)
    @ApiOperation(value = "Delete Provisioning Configuration", nickname = "DeleteProvisioningConf", response = String.class)
    public ResponseEntity<String> deleteApplication(@Valid String applicationName) {
        return ResponseEntity.status(HttpStatus.OK).body(provisioiningConfService.delete(applicationName));
    }


}
