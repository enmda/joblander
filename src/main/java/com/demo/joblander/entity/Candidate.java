package com.demo.joblander.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    
    private String headline;        

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String phone;
    private String location;
    private String linkedInUrl;
    private String portfolioUrl;
    private String resumeUrl;       

    
    
    @ElementCollection
    @CollectionTable(name = "candidate_experiences", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "experience", columnDefinition = "TEXT")
    @Builder.Default
    private List<String> workExperiences = new ArrayList<>();

    
    @ElementCollection
    @CollectionTable(name = "candidate_educations", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "education")
    @Builder.Default
    private List<String> educations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "candidate_skills", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "skill")
    @Builder.Default
    private List<String> skills = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "candidate_languages", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "language")
    @Builder.Default
    private List<String> languages = new ArrayList<>();

    
    @ElementCollection
    @CollectionTable(name = "candidate_certifications", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "certification")
    @Builder.Default
    private List<String> certifications = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
