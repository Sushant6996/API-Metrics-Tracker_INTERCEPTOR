package com.technical.api_metrics_tracker.repository;



import com.technical.api_metrics_tracker.model.APIMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface APIMetricsRepository extends JpaRepository<APIMetrics, Long> {

    // Method to find metrics by API name
    @Query("SELECT m FROM APIMetrics m WHERE m.apiName = ?1")
    List<APIMetrics> findByApiName(String apiName);


    // Additional custom query methods can be added here
}

