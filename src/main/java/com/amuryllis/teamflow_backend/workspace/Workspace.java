package com.amuryllis.teamflow_backend.workspace;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.Getter;

@Entity
@Table(name = "workspaces")
@Getter
public class Workspace {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120)
  private String name;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  protected Workspace() {
  }

  public Workspace(String name) {
    this.name = name;
    this.createdAt = OffsetDateTime.now();
  }
}
