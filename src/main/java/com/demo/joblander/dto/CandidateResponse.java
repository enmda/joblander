package com.demo.joblander.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// Response — what you return to the client
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponse {

    private UUID id;
    private UUID userId;
    private String username;    // from User, handy to have

    private String headline;
    private String summary;
    private String phone;
    private String location;
    private String linkedInUrl;
    private String portfolioUrl;
    private String resumeUrl;   // read-only from client perspective, set by AI service

    private List<String> workExperiences;
    private List<String> educations;
    private List<String> skills;
    private List<String> languages;
    private List<String> certifications;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
