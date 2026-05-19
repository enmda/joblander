package com.demo.joblander.service;

import com.demo.joblander.dto.CandidateRequest;
import com.demo.joblander.dto.CandidateResponse;
import com.demo.joblander.entity.Candidate;
import com.demo.joblander.entity.Job;
import com.demo.joblander.entity.User;
import com.demo.joblander.repository.CandidateRepository;
import com.demo.joblander.repository.JobRepository;
import com.demo.joblander.repository.UserRepository;
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
    private final UserRepository userRepository;

    public CandidateResponse createCandidate(UUID userId, CandidateRequest request) {
        if (candidateRepository.existsByUserId(userId)) {
            throw new IllegalStateException("Candidate profile already exists for this user");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Candidate candidate = Candidate.builder()
            .user(user)
            .headline(request.getHeadline())
            .summary(request.getSummary())
            .phone(request.getPhone())
            .location(request.getLocation())
            .linkedInUrl(request.getLinkedInUrl())
            .portfolioUrl(request.getPortfolioUrl())
            .resumeUrl(request.getResumeUrl())
            .workExperiences(request.getWorkExperiences())
            .educations(request.getEducations())
            .skills(request.getSkills())
            .languages(request.getLanguages())
            .certifications(request.getCertifications())
            .build();

        return toResponse(candidateRepository.save(candidate));
    }

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
        candidate.setResumeUrl(request.getResumeUrl());
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

    public List<Map<String, Object>> matchJobs(UUID candidateId) {
        Candidate candidate = findById(candidateId);
        Set<String> candidateSkillSet = normalizeSkills(candidate.getSkills());

        return jobRepository.findAll()
            .stream()
            .map(job -> buildMatchResult(job, candidateSkillSet))
            .sorted((a, b) ->
                Integer.compare((int) b.get("matchScore"), (int) a.get("matchScore")))
            .collect(Collectors.toList());
    }

    private Map<String, Object> buildMatchResult(Job job, Set<String> candidateSkills) {
        Set<String> required = normalizeSkills(job.getRequiredSkills());

        Set<String> matched = required.stream()
            .filter(candidateSkills::contains)
            .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> missing = required.stream()
            .filter(s -> !candidateSkills.contains(s))
            .collect(Collectors.toCollection(LinkedHashSet::new));

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

    private Set<String> normalizeSkills(Collection<String> skills) {
        if (skills == null) {
            return Collections.emptySet();
        }

        return skills.stream()
            .filter(Objects::nonNull)
            .flatMap(skill -> Arrays.stream(skill.split(",")))
            .map(String::trim)
            .map(String::toLowerCase)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    
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
