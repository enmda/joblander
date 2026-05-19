package com.demo.joblander.controller;

import com.demo.joblander.dto.CandidateRequest;
import com.demo.joblander.dto.CandidateResponse;
import com.demo.joblander.entity.User;
import com.demo.joblander.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<CandidateResponse> create(
            @AuthenticationPrincipal User userDetails,
            @Valid @RequestBody CandidateRequest request) {
        UUID userid = userDetails.getId();
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(candidateService.createCandidate(userid, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody CandidateRequest request) {
        return ResponseEntity.ok(candidateService.updateCandidate(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/match")
    public ResponseEntity<List<Map<String, Object>>> match(@PathVariable UUID id) {
        return ResponseEntity.ok(candidateService.matchJobs(id));
    }
}