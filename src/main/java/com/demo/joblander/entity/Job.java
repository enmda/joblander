package com.demo.joblander.entity;

import com.demo.joblander.entity.enums.JobStatus;
import com.demo.joblander.entity.enums.JobType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String company;
    private String location;
    private String salary;

    @Enumerated(EnumType.STRING)
    private JobType jobType;          // FULL_TIME, PART_TIME, REMOTE, CONTRACT

    @Enumerated(EnumType.STRING)
    private JobStatus status;         // OPEN, CLOSED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by")
    private User postedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
