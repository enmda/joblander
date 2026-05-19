package com.demo.joblander.repository;

import com.demo.joblander.entity.Application;
import com.demo.joblander.entity.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByApplicantId(UUID userId);       

    List<Application> findByJobId(Long jobId);

    List<Application> findByStatus(ApplicationStatus status);

    Optional<Application> findByApplicantIdAndJobId(UUID userId, Long jobId);  

    boolean existsByApplicantIdAndJobId(UUID userId, Long jobId);              
}