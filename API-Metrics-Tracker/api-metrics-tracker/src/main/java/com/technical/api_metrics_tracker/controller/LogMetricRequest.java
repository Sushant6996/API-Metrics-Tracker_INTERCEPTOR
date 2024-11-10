package com.technical.api_metrics_tracker.controller;

public class LogMetricRequest {
    private String apiName;
    private long responseTime;


    // Getters and Setters
    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

}
