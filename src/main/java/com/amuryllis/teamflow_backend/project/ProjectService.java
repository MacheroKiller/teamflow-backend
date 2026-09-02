package com.amuryllis.teamflow_backend.project;

import com.amuryllis.teamflow_backend.project.dto.ProjectRequest;
import com.amuryllis.teamflow_backend.workspace.Workspace;
import com.amuryllis.teamflow_backend.workspace.WorkspaceRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final WorkspaceRepository workspaceRepository;

  public ProjectService(
      ProjectRepository projectRepository, WorkspaceRepository workspaceRepository) {
    this.projectRepository = projectRepository;
    this.workspaceRepository = workspaceRepository;
  }

  @Transactional(readOnly = true)
  public List<Project> listByWorkspace(Long workspaceId) {
    return projectRepository.findByWorkspaceId(workspaceId);
  }

  @Transactional
  public Project create(ProjectRequest request) {
    Workspace workspace =
        workspaceRepository
            .findById(request.workspaceId())
            .orElseThrow(
                () -> new NoSuchElementException("Workspace not found: " + request.workspaceId()));

    Project project = new Project(workspace, request.name());
    return projectRepository.save(project);
  }
}
