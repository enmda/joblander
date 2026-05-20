package com.demo.joblander.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdminOverviewResponse {
    private List<AdminCandidateResponse> candidates;
    private List<AdminUserResponse> users;
}
