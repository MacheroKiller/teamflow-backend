package com.amuryllis.teamflow_backend.story;

public record StoryAssignRequest(Long assigneeId // null permitido = desasignar
    ) {}
