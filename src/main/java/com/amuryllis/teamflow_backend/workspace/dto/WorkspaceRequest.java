package com.amuryllis.teamflow_backend.workspace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkspaceRequest(@NotBlank @Size(max = 120) String name) {}
