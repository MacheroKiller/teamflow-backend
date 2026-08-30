package com.amuryllis.teamflow_backend.story;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

  private final StoryService storyService;

  public StoryController(StoryService storyService) {
    this.storyService = storyService;
  }

  @GetMapping
  public List<StoryResponse> listByProject(@RequestParam Long projectId) {
    return storyService.listByProject(projectId).stream().map(StoryResponse::from).toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public StoryResponse create(@Valid @RequestBody StoryRequest request) {
    return StoryResponse.from(storyService.create(request));
  }

  @PatchMapping("/{id}/status")
  public StoryResponse updateStatus(
      @PathVariable Long id, @Valid @RequestBody StoryStatusUpdateRequest request) {
    return StoryResponse.from(storyService.updateStatus(id, request.status()));
  }

  @PatchMapping("/{id}/assignee")
  public StoryResponse assign(@PathVariable Long id, @RequestBody StoryAssignRequest request) {
    return StoryResponse.from(storyService.assign(id, request.assigneeId()));
  }
}
