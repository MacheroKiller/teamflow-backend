package com.amuryllis.teamflow_backend.project;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping
  public List<ProjectResponse> listByWorkspace(@RequestParam Long workspaceId) {
    return projectService.listByWorkspace(workspaceId).stream().map(ProjectResponse::from).toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProjectResponse create(@Valid @RequestBody ProjectRequest request) {
    return ProjectResponse.from(projectService.create(request));
  }
}
