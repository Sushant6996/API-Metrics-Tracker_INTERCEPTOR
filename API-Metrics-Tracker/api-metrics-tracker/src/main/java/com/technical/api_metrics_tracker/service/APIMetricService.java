package com.technical.api_metrics_tracker.service;

import com.technical.api_metrics_tracker.model.APIMetrics;
import com.technical.api_metrics_tracker.repository.APIMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class APIMetricService {

    private final APIMetricsRepository metricsRepository;

    @Autowired
    public APIMetricService(APIMetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    // Method to log a new metric
    public String logMetric(String apiName, int statusCode, long startTime) {
        // retrieving existing metrics for the API
        List<APIMetrics> existingMetrics = metricsRepository.findByApiName(apiName);

        // Calculate min, max, and average response times

        long responseTime=System.currentTimeMillis() - startTime;
        long minResponseTime = Long.MAX_VALUE;
        minResponseTime = existingMetrics.isEmpty() ? responseTime : Math.min(responseTime, existingMetrics.stream().mapToLong(APIMetrics::getMinResponseTime).min().orElse(responseTime));
        long maxResponseTime = existingMetrics.isEmpty() ? responseTime : Math.max(responseTime, existingMetrics.stream().mapToLong(APIMetrics::getMaxResponseTime).max().orElse(responseTime));
        double avgResponseTime = existingMetrics.isEmpty() ? responseTime : ((existingMetrics.stream().mapToDouble(APIMetrics::getAvgResponseTime).sum() + responseTime) / (existingMetrics.size() + 1));

        // Request status based on HTTP status
        String statusText = HttpStatus.valueOf(statusCode).getReasonPhrase();
        String requestStatus = statusCode + " " + statusText;

        // Increment request count based on existing metrics count
        long requestCount = existingMetrics.isEmpty() ? 1 : existingMetrics.get(existingMetrics.size() - 1).getRequestCount() + 1;

        // Create and save a new APIMetrics entry
        APIMetrics metric = new APIMetrics(apiName, requestStatus, requestCount, maxResponseTime, minResponseTime, avgResponseTime, LocalDateTime.now());
        metricsRepository.save(metric);

        return "Metric Logged Successfully";
    }

    public String getAggregateMetrics(String apiName) {
        List<APIMetrics> metrics = metricsRepository.findByApiName(apiName);
        if (metrics.isEmpty()) {
            return "No metrics found for the given API.";
        }

        long minResponseTime = metrics.stream().mapToLong(APIMetrics::getMinResponseTime).min().orElse(0);
        long maxResponseTime = metrics.stream().mapToLong(APIMetrics::getMaxResponseTime).max().orElse(0);
        double avgResponseTime = metrics.stream().mapToDouble(APIMetrics::getAvgResponseTime).average().orElse(0.0);

        return "Aggregated Metrics for API " + apiName + ": Min Response Time: " + minResponseTime + "ms, Max Response Time: " + maxResponseTime + "ms, Avg Response Time: " + avgResponseTime + "ms";
    }
}
