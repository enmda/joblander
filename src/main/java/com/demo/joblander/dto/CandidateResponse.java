package com.demo.joblander.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CandidateResponse {
    private UUID id;
    private String headline;
    private String summary;
    private String phone;
    private String location;
    private String linkedInUrl;
    private String portfolioUrl;
    private String resumeUrl;
    private List<String> workExperiences;
    private List<String> educations;
    private Set<String> skills;
    private Set<String> languages;
    private List<String> certifications;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
