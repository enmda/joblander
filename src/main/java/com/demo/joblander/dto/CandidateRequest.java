package com.demo.joblander.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateRequest {

    private String headline;

    @Size(max = 2000)
    private String summary;

    private String phone;
    private String location;
    private String linkedInUrl;
    private String portfolioUrl;
    private String resumeUrl;

    @Builder.Default
    private List<String> workExperiences = new ArrayList<>();
    @Builder.Default
    private List<String> educations = new ArrayList<>();
    @Builder.Default
    private Set<String> skills = new HashSet<>();
    @Builder.Default
    private Set<String> languages = new HashSet<>();
    @Builder.Default
    private List<String> certifications = new ArrayList<>();
}
