package com.lightningcd.api.request;

import com.lightningcd.api.model.ProvisionEnv;
import com.lightningcd.api.model.ProvisioningConf;

import javax.validation.constraints.NotNull;

public class ProvisioningConfRequest {

    @NotNull
    private String applicationName;

    @NotNull
    private ProvisionEnv[] provisioningEnv;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public ProvisionEnv[] getProvisioningEnv() {
        return provisioningEnv;
    }

    public void setProvisioningEnv(ProvisionEnv[] provisioningEnv) {
        this.provisioningEnv = provisioningEnv;
    }

    public ProvisioningConf toProvisioningConf() {
        return new ProvisioningConf(applicationName, provisioningEnv);
    }

    public ProvisioningConf copyTo(ProvisioningConf provisioningConf) {
        ProvisioningConf updated = toProvisioningConf();
        updated.setId(provisioningConf.getId());
        return updated;
    }
}
