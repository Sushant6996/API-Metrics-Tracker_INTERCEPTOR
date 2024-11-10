package com.technical.api_metrics_tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "api_metrics",
        indexes = {
                @Index(name = "idx_api_name", columnList = "api_name"),
                @Index(name = "idx_timestamp", columnList = "timestamp")
        }
)
public class APIMetrics {

    @Id
    @Column(name = "api_name") // Change this to reflect the new primary key
    private String apiName; // Change Long to String since apiName is usually a String

    @Column(name = "request_status", nullable = false)
    private String requestStatus;

    @Column(name = "request_count", nullable = false)
    private Long requestCount;

    @Column(name = "max_response_time", nullable = false)
    private Long maxResponseTime;

    @Column(name = "min_response_time", nullable = false)
    private Long minResponseTime;

    @Column(name = "avg_response_time", nullable = false)
    private Double avgResponseTime;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp; // Keep as LocalDateTime

    // Default constructor
    public APIMetrics() {
    }

    // Parameterized constructor
    public APIMetrics(String apiName, String requestStatus, Long requestCount, Long maxResponseTime,
                      Long minResponseTime, Double avgResponseTime, LocalDateTime timestamp) {
        this.apiName = apiName;
        this.requestStatus = requestStatus;
        this.requestCount = requestCount;
        this.maxResponseTime = maxResponseTime;
        this.minResponseTime = minResponseTime;
        this.avgResponseTime = avgResponseTime;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Long requestCount) {
        this.requestCount = requestCount;
    }

    public Long getMaxResponseTime() {
        return maxResponseTime;
    }

    public void setMaxResponseTime(Long maxResponseTime) {
        this.maxResponseTime = maxResponseTime;
    }

    public Long getMinResponseTime() {
        return minResponseTime;
    }

    public void setMinResponseTime(Long minResponseTime) {
        this.minResponseTime = minResponseTime;
    }

    public Double getAvgResponseTime() {
        return avgResponseTime;
    }

    public void setAvgResponseTime(Double avgResponseTime) {
        this.avgResponseTime = avgResponseTime;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
