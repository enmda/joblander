package com.demo.joblander.dto;

import com.demo.joblander.entity.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ApplicationResponse {

    private Long id;
    private UUID applicantId;        // UUID not Long
    private String applicantUsername;// useful to include
    private Long jobId;
    private String jobTitle;
    private String coverLetter;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
}