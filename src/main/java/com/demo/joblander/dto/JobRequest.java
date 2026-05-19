package com.demo.joblander.dto;

import com.demo.joblander.entity.enums.JobStatus;
import com.demo.joblander.entity.enums.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data

public class JobRequest {

    @NotBlank
    private String title;

    private String description;
    private String company;
    private String location;
    private String salary;

    private Set<String> requiredSkills = new HashSet<>();

    private JobType jobType;

    private JobStatus status;

    @NotNull
    private LocalDateTime deadline;
}
