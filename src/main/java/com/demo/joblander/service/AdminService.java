package com.demo.joblander.service;

import com.demo.joblander.dto.AdminCandidateResponse;
import com.demo.joblander.dto.AdminOverviewResponse;
import com.demo.joblander.dto.AdminUserResponse;
import com.demo.joblander.entity.Candidate;
import com.demo.joblander.entity.User;
import com.demo.joblander.repository.CandidateRepository;
import com.demo.joblander.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;

    public AdminOverviewResponse getOverview() {
        return AdminOverviewResponse.builder()
            .candidates(candidateRepository.findAll()
                .stream()
                .map(this::toAdminCandidateResponse)
                .toList())
            .users(userRepository.findAll()
                .stream()
                .map(this::toAdminUserResponse)
                .toList())
            .build();
    }

    private AdminCandidateResponse toAdminCandidateResponse(Candidate candidate) {
        return AdminCandidateResponse.builder()
            .id(candidate.getId())
            .userId(candidate.getUser().getId())
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

    private AdminUserResponse toAdminUserResponse(User user) {
        return AdminUserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .roles(user.getRoles())
            .enabled(user.isEnabled())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }
}
