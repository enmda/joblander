package com.demo.joblander.controller;

import com.demo.joblander.dto.ApplicationRequest;
import com.demo.joblander.dto.ApplicationResponse;
import com.demo.joblander.entity.enums.ApplicationStatus;
import com.demo.joblander.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> apply(
            @Valid @RequestBody ApplicationRequest request,
            @RequestParam UUID userId) {           // UUID
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(applicationService.apply(request, userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ApplicationResponse>> getByApplicant(
            @PathVariable UUID userId) {           // UUID
        return ResponseEntity.ok(applicationService.getByApplicant(userId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getByJob(jobId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.updateStatus(id, status));
    }
}