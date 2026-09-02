package com.amuryllis.teamflow_backend.story;

import com.amuryllis.teamflow_backend.project.Project;
import com.amuryllis.teamflow_backend.project.ProjectRepository;
import com.amuryllis.teamflow_backend.story.dto.StoryRequest;
import com.amuryllis.teamflow_backend.story.enums.StoryStatus;
import com.amuryllis.teamflow_backend.user.AppUser;
import com.amuryllis.teamflow_backend.user.AppUserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoryService {

  private final StoryRepository storyRepository;
  private final ProjectRepository projectRepository;
  private final AppUserRepository appUserRepository;

  public StoryService(
      StoryRepository storyRepository,
      ProjectRepository projectRepository,
      AppUserRepository appUserRepository) {
    this.storyRepository = storyRepository;
    this.projectRepository = projectRepository;
    this.appUserRepository = appUserRepository;
  }

  @Transactional(readOnly = true)
  public List<Story> listByProject(Long projectId) {
    return storyRepository.findByProjectId(projectId);
  }

  @Transactional
  public Story create(StoryRequest request) {
    Project project =
        projectRepository
            .findById(request.projectId())
            .orElseThrow(
                () -> new NoSuchElementException("Project not found: " + request.projectId()));

    Story story = new Story(project, request.title(), request.description());
    return storyRepository.save(story);
  }

  @Transactional
  public Story updateStatus(Long storyId, StoryStatus newStatus) {
    Story story =
        storyRepository
            .findById(storyId)
            .orElseThrow(() -> new NoSuchElementException("Story not found: " + storyId));

    story.updateStatus(newStatus);
    return storyRepository.save(story);
  }

  @Transactional
  public Story assign(Long storyId, Long assigneeId) {
    Story story =
        storyRepository
            .findById(storyId)
            .orElseThrow(() -> new NoSuchElementException("Story not found: " + storyId));

    AppUser assignee = null;
    if (assigneeId != null) {
      assignee =
          appUserRepository
              .findById(assigneeId)
              .orElseThrow(() -> new NoSuchElementException("User not found: " + assigneeId));
    }

    story.assignTo(assignee);
    return storyRepository.save(story);
  }
}
