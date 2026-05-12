package com.demo.joblander.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private List<String> workExperiences = new ArrayList<>();
    private List<String> educations = new ArrayList<>();
    private List<String> skills = new ArrayList<>();
    private List<String> languages = new ArrayList<>();
    private List<String> certifications = new ArrayList<>();
}
