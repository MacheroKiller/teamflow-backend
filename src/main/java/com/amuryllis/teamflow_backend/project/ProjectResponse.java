package com.amuryllis.teamflow_backend.project;

import java.time.OffsetDateTime;

public record ProjectResponse(Long id, Long workspaceId, String name, OffsetDateTime createdAt) {
  public static ProjectResponse from(Project project) {
    return new ProjectResponse(
        project.getId(), project.getWorkspace().getId(), project.getName(), project.getCreatedAt());
  }
}
