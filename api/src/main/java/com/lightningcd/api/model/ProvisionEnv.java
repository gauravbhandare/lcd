package com.lightningcd.api.model;


import java.net.URL;

/**
 * This class will serve as a model for holding endpoints for
 * provisioning environments, idea being an application can have
 * different rest endpoints for different application environments.
 */

public class ProvisionEnv {

    private String environmentName;
    private String restEndPoint;


    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getRestEndPoint() {
        return restEndPoint;
    }

    public void setRestEndPoint(String restEndPoint) {
        this.restEndPoint = restEndPoint;
    }
}
