package com.demo.joblander.repository;

import com.demo.joblander.entity.Job;
import com.demo.joblander.entity.enums.JobStatus;
import com.demo.joblander.entity.enums.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByLocation(String location);

    List<Job> findByStatus(JobStatus status);

    List<Job> findByJobType(JobType jobType);

    List<Job> findByCompany(String company);

    @Query("SELECT j FROM Job j WHERE :skill MEMBER OF j.requiredSkills")
    List<Job> findByRequiredSkill(@Param("skill") String skill);

    @Query("SELECT j FROM Job j WHERE j.status = :status AND j.location = :location")
    List<Job> findByStatusAndLocation(
        @Param("status") JobStatus status,
        @Param("location") String location
    );
}
