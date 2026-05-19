package com.demo.joblander.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobMatchResponse {
    private Long jobId;
    private String title;
    private String company;
    private String location;
    private int matchScore;
    private List<String> matchedSkills;
    private List<String> missingSkills;
}
