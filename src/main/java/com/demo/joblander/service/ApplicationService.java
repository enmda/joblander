package com.demo.joblander.service;

import com.demo.joblander.dto.ApplicationRequest;
import com.demo.joblander.dto.ApplicationResponse;
import com.demo.joblander.entity.Application;
import com.demo.joblander.entity.Job;
import com.demo.joblander.entity.User;
import com.demo.joblander.entity.enums.ApplicationStatus;
import com.demo.joblander.repository.ApplicationRepository;
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
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationResponse apply(ApplicationRequest request, UUID userId) {  
        if (applicationRepository.existsByApplicantIdAndJobId(userId, request.getJobId())) {
            throw new IllegalStateException("You have already applied to this job");
        }

        User applicant = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Job job = jobRepository.findById(request.getJobId())
            .orElseThrow(() -> new EntityNotFoundException("Job not found"));

        Application application = Application.builder()
            .applicant(applicant)
            .job(job)
            .coverLetter(request.getCoverLetter())
            .status(ApplicationStatus.PENDING)
            .build();

        return toResponse(applicationRepository.save(application));
    }

    public List<ApplicationResponse> getByApplicant(UUID userId) {  
        return applicationRepository.findByApplicantId(userId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<ApplicationResponse> getByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public ApplicationResponse updateStatus(Long id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Application not found"));
        application.setStatus(status);
        return toResponse(applicationRepository.save(application));
    }

    private ApplicationResponse toResponse(Application application) {
        return ApplicationResponse.builder()
            .id(application.getId())
            .applicantId(application.getApplicant().getId())         
            .applicantUsername(application.getApplicant().getUsername())
            .jobId(application.getJob().getId())
            .jobTitle(application.getJob().getTitle())
            .coverLetter(application.getCoverLetter())
            .status(application.getStatus())
            .appliedAt(application.getAppliedAt())
            .build();
    }
}