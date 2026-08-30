package com.amuryllis.teamflow_backend.story;

import jakarta.validation.constraints.NotNull;

public record StoryStatusUpdateRequest(@NotNull StoryStatus status) {
}
