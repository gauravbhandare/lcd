package com.lightningcd.api.model;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "deployapplication")
public class DeployApplication extends BaseModel {

    /**
     * Model for defining what an application is
     * An Application consist of web component, app component and database component
     */


    @Indexed(unique = true)
    private String applicationName;
    private Environment[] environments;
    private Component[] component;
    private String provisioningTypes;


    public DeployApplication(String applicationName, Environment[] environments, Component[] component, String provisioningTypes) {
        this.applicationName = applicationName;
        this.environments = environments;
        this.component = component;
        this.provisioningTypes = provisioningTypes;
    }

    public DeployApplication() {
        // default constructor
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Environment[] getEnvironments() {
        return environments;
    }

    public void setEnvironments(Environment[] environments) {
        this.environments = environments;
    }

    public Component[] getComponent() {
        return component;
    }

    public void setComponent(Component[] component) {
        this.component = component;
    }

    public String getProvisioningTypes() {
        return provisioningTypes;
    }

    public void setProvisioningTypes(String provisioningTypes) {
        this.provisioningTypes = provisioningTypes;
    }

    public String toString() {
        return ("DeploymentApplication:" + "applicationName:" + applicationName + " environments:" + environments.toString() + " components:" +
                component.toString() + " provisioningTypes:" + provisioningTypes);
    }
}

