package com.demo.joblander.repository;

import com.demo.joblander.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    Optional<Candidate> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);
}
