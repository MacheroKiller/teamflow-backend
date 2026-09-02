package com.amuryllis.teamflow_backend.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectRequest(@NotNull Long workspaceId, @NotBlank @Size(max = 120) String name) {}
