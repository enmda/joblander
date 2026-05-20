package com.demo.joblander.dto;

import com.demo.joblander.entity.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class AdminUserResponse {
    private UUID id;
    private String username;
    private String email;
    private Set<Role> roles;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
