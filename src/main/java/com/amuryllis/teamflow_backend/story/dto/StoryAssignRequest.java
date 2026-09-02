package com.amuryllis.teamflow_backend.story.dto;

public record StoryAssignRequest(Long assigneeId // null permitido = desasignar
    ) {}
