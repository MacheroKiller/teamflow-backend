package com.amuryllis.teamflow_backend.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;

@Entity
@Table(name = "app_users")
@Getter
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 60)
  private String username;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  protected AppUser() {}

  public AppUser(String username, String passwordHash) {
    this.username = username;
    this.passwordHash = passwordHash;
    this.createdAt = OffsetDateTime.now();
  }
}
