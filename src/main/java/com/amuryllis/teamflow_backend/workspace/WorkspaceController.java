package com.amuryllis.teamflow_backend.workspace;

import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

  private final WorkspaceRepository repository;

  public WorkspaceController(WorkspaceRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<Workspace> listAll() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Workspace getById(@PathVariable Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("Workspace not found: " + id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Workspace create(@Valid @RequestBody WorkspaceRequest request) {
    return repository.save(new Workspace(request.name()));
  }
}
