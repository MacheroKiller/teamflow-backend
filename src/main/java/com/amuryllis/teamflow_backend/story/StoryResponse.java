package com.amuryllis.teamflow_backend.story;

import java.time.OffsetDateTime;

public record StoryResponse(
    Long id,
    Long projectId,
    Long assigneeId,
    String title,
    String description,
    StoryStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt) {
  public static StoryResponse from(Story story) {
    return new StoryResponse(
        story.getId(),
        story.getProject().getId(),
        story.getAssignee() != null ? story.getAssignee().getId() : null,
        story.getTitle(),
        story.getDescription(),
        story.getStatus(),
        story.getCreatedAt(),
        story.getUpdatedAt());
  }
}
