package com.demo.joblander.service;

import com.demo.joblander.dto.JobRequest;
import com.demo.joblander.dto.JobResponse;
import com.demo.joblander.entity.Job;
import com.demo.joblander.entity.User;
import com.demo.joblander.repository.JobRepository;
import com.demo.joblander.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobResponse createJob(JobRequest request, UUID userId) {  
        User poster = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Job job = Job.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .company(request.getCompany())
            .location(request.getLocation())
            .salary(request.getSalary())
            .requiredSkills(request.getRequiredSkills())
            .jobType(request.getJobType())
            .status(request.getStatus())
            .deadline(request.getDeadline())
            .postedBy(poster)
            .build();

        return toResponse(jobRepository.save(job));
    }

    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public JobResponse getJobById(Long id) {
        return toResponse(findById(id));
    }

    public JobResponse updateJob(Long id, JobRequest request) {
        Job job = findById(id);
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setRequiredSkills(request.getRequiredSkills());
        job.setJobType(request.getJobType());
        job.setStatus(request.getStatus());
        job.setDeadline(request.getDeadline());
        return toResponse(jobRepository.save(job));
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    public JobResponse toResponse(Job job) {
        return JobResponse.builder()
            .id(job.getId())
            .title(job.getTitle())
            .description(job.getDescription())
            .company(job.getCompany())
            .location(job.getLocation())
            .salary(job.getSalary())
            .requiredSkills(job.getRequiredSkills())
            .jobType(job.getJobType())
            .status(job.getStatus())
            .postedById(job.getPostedBy() != null ? job.getPostedBy().getId() : null)
            .createdAt(job.getCreatedAt())
            .deadline(job.getDeadline())
            .build();
    }

    private Job findById(Long id) {
        return jobRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Job not found with id: " + id));
    }
}