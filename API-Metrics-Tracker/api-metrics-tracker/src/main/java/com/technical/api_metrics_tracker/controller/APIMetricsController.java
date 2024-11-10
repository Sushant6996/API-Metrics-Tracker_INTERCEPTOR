package com.technical.api_metrics_tracker.controller;

import com.technical.api_metrics_tracker.service.APIMetricService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/metrics")
public class APIMetricsController {

    private final APIMetricService apiMetricService;
    private static final Logger logger = LoggerFactory.getLogger(APIMetricsController.class);

    @Autowired
    public APIMetricsController(APIMetricService apiMetricService) {
        this.apiMetricService = apiMetricService;
    }

    // Endpoint to log a new metric
    @PostMapping("/log")
    public ResponseEntity<String> logMetric(@RequestBody LogMetricRequest request, HttpServletRequest servletRequest) {
        String apiName = request.getApiName();

        // Calculate response time
        long startTime = (long) servletRequest.getAttribute("startTime");  // Get the start time from the interceptor

        try {
            // Set default if apiName is missing or empty
            if (apiName == null || apiName.isEmpty()) {
                throw new IllegalArgumentException("API name is required.");
            }

            // Log metric with the provided data
            apiMetricService.logMetric(apiName, HttpStatus.CREATED.value(), startTime);
            return new ResponseEntity<>("Metric Logged Successfully", HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Log error and respond with 400 Bad Request for missing/invalid input
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // General exception handling for unexpected errors
            return new ResponseEntity<>("Error Occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to retrieve aggregated metrics for a specific API
    @GetMapping("/aggregate")
    public ResponseEntity<String> getAggregateMetrics(@RequestParam String apiName) {
        try {
            String result = apiMetricService.getAggregateMetrics(apiName);
            if (result == null || result.isEmpty()) {
                return new ResponseEntity<>("No metrics found for API: " + apiName, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any unexpected errors during aggregation
            return new ResponseEntity<>("Error Occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
