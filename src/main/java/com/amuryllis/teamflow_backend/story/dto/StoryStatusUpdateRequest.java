package com.amuryllis.teamflow_backend.story.dto;

import com.amuryllis.teamflow_backend.story.enums.StoryStatus;
import jakarta.validation.constraints.NotNull;

public record StoryStatusUpdateRequest(@NotNull StoryStatus status) {}
