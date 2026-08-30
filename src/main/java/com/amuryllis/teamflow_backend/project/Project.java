package com.amuryllis.teamflow_backend.project;

import com.amuryllis.teamflow_backend.workspace.Workspace;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.Getter;

@Entity
@Table(name = "projects")
@Getter
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "workspace_id", nullable = false)
  private Workspace workspace;

  @Column(nullable = false, length = 120)
  private String name;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  protected Project() {
  }

  public Project(Workspace workspace, String name) {
    this.workspace = workspace;
    this.name = name;
    this.createdAt = OffsetDateTime.now();
  }
}
