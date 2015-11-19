package com.lightningcd.api.model;


public class Environment {

    /**
     * Environment are defined for an application such as dev, qa , perf
     */


    private String environmentName;

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }
}
