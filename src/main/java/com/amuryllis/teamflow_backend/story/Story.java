package com.amuryllis.teamflow_backend.story;

import com.amuryllis.teamflow_backend.project.Project;
import com.amuryllis.teamflow_backend.user.AppUser;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.Getter;

@Entity
@Table(name = "stories")
@Getter
public class Story {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignee_id")
  private AppUser assignee;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private StoryStatus status;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  protected Story() {
  }

  public Story(Project project, String title, String description) {
    this.project = project;
    this.title = title;
    this.description = description;
    this.status = StoryStatus.TODO;
    this.createdAt = OffsetDateTime.now();
    this.updatedAt = OffsetDateTime.now();
  }

  public void updateStatus(StoryStatus newStatus) {
    this.status = newStatus;
    this.updatedAt = OffsetDateTime.now();
  }

  public void assignTo(AppUser user) {
    this.assignee = user;
    this.updatedAt = OffsetDateTime.now();
  }
}
