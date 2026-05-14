package com.demo.joblander.service;

import com.demo.joblander.dto.CandidateRequest;
import com.demo.joblander.dto.CandidateResponse;
import com.demo.joblander.entity.Candidate;
import com.demo.joblander.entity.Job;
import com.demo.joblander.repository.CandidateRepository;
import com.demo.joblander.repository.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;

    public CandidateResponse getCandidateById(UUID id) {
        return toResponse(findById(id));
    }

    public CandidateResponse updateCandidate(UUID id, CandidateRequest request) {
        Candidate candidate = findById(id);
        candidate.setHeadline(request.getHeadline());
        candidate.setSummary(request.getSummary());
        candidate.setPhone(request.getPhone());
        candidate.setLocation(request.getLocation());
        candidate.setLinkedInUrl(request.getLinkedInUrl());
        candidate.setPortfolioUrl(request.getPortfolioUrl());
        candidate.setWorkExperiences(request.getWorkExperiences());
        candidate.setEducations(request.getEducations());
        candidate.setSkills(request.getSkills());
        candidate.setLanguages(request.getLanguages());
        candidate.setCertifications(request.getCertifications());
        return toResponse(candidateRepository.save(candidate));
    }

    public void deleteCandidate(UUID id) {
        candidateRepository.deleteById(id);
    }

    // --- Match Logic ---
    public List<Map<String, Object>> matchJobs(UUID candidateId) {
        Candidate candidate = findById(candidateId);
        List<String> candidateSkills = candidate.getSkills()
            .stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());

        return jobRepository.findAll()
            .stream()
            .map(job -> buildMatchResult(job, candidateSkills))
            .sorted((a, b) ->
                Integer.compare((int) b.get("matchScore"), (int) a.get("matchScore")))
            .collect(Collectors.toList());
    }

    private Map<String, Object> buildMatchResult(Job job, List<String> candidateSkills) {
        List<String> required = job.getRequiredSkills()
            .stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());

        List<String> matched = required.stream()
            .filter(candidateSkills::contains)
            .collect(Collectors.toList());

        List<String> missing = required.stream()
            .filter(s -> !candidateSkills.contains(s))
            .collect(Collectors.toList());

        int score = required.isEmpty() ? 0 : (matched.size() * 100 / required.size());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("jobId", job.getId());
        result.put("title", job.getTitle());
        result.put("company", job.getCompany());
        result.put("location", job.getLocation());
        result.put("matchScore", score);
        result.put("matchedSkills", matched);
        result.put("missingSkills", missing);
        return result;
    }

    // --- Mapper ---
    public CandidateResponse toResponse(Candidate candidate) {
        return CandidateResponse.builder()
            .id(candidate.getId())
            .headline(candidate.getHeadline())
            .summary(candidate.getSummary())
            .phone(candidate.getPhone())
            .location(candidate.getLocation())
            .linkedInUrl(candidate.getLinkedInUrl())
            .portfolioUrl(candidate.getPortfolioUrl())
            .resumeUrl(candidate.getResumeUrl())
            .workExperiences(candidate.getWorkExperiences())
            .educations(candidate.getEducations())
            .skills(candidate.getSkills())
            .languages(candidate.getLanguages())
            .certifications(candidate.getCertifications())
            .createdAt(candidate.getCreatedAt())
            .updatedAt(candidate.getUpdatedAt())
            .build();
    }

    private Candidate findById(UUID id) {
        return candidateRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + id));
    }
}