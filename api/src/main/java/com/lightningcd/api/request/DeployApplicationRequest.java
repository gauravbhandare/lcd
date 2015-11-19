package com.lightningcd.api.request;


import com.lightningcd.api.model.Component;
import com.lightningcd.api.model.DeployApplication;
import com.lightningcd.api.model.Environment;

import javax.validation.constraints.NotNull;

public class DeployApplicationRequest {

    @NotNull
    private String applicationName;

    @NotNull
    private Environment[] environments;

    @NotNull
    private Component[] component;

    @NotNull
    private String provisioningTypes;

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

    public DeployApplication toDeployApplication() {
        return new DeployApplication(applicationName, environments, component, provisioningTypes);
    }

    public DeployApplication copyTo(DeployApplication deployApplication) {
        DeployApplication updated = toDeployApplication();
        updated.setId(deployApplication.getId());
        return updated;
    }

}
