package com.demo.joblander.dto;

import com.demo.joblander.entity.enums.JobStatus;
import com.demo.joblander.entity.enums.JobType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String company;
    private String location;
    private String salary;
    private List<String> requiredSkills;
    private JobType jobType;
    private JobStatus status;
    private UUID postedById;         // UUID not Long
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
}