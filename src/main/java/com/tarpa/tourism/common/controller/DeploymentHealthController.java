package com.tarpa.tourism.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class DeploymentHealthController {

    private final DataSource dataSource;

    public DeploymentHealthController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> checkHealth() {
        Map<String, Object> status = new HashMap<>();
        status.put("application", "Satori Adventures Operations System");
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now().toString());

        // Test database connectivity
        try (Connection connection = dataSource.getConnection()) {
            status.put("database", "CONNECTED");
            status.put("databaseProductName", connection.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            status.put("database", "FAILED");
            status.put("databaseError", e.getMessage());
            return ResponseEntity.status(503).body(status);
        }

        return ResponseEntity.ok(status);
    }
}