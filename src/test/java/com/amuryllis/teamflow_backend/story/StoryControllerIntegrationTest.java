package com.amuryllis.teamflow_backend.story;

import static org.assertj.core.api.Assertions.assertThat;

import com.amuryllis.teamflow_backend.project.Project;
import com.amuryllis.teamflow_backend.project.ProjectRepository;
import com.amuryllis.teamflow_backend.user.AppUser;
import com.amuryllis.teamflow_backend.user.AppUserRepository;
import com.amuryllis.teamflow_backend.workspace.Workspace;
import com.amuryllis.teamflow_backend.workspace.WorkspaceRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class StoryControllerIntegrationTest {

  @Container @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

  @Autowired private RestTestClient restTestClient;

  @Autowired private WorkspaceRepository workspaceRepository;

  @Autowired private ProjectRepository projectRepository;

  @Autowired private StoryRepository storyRepository;

  @Autowired private AppUserRepository appUserRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  private Long projectId;
  private String authToken;

  @BeforeEach
  void setUp() {
    Workspace workspace = workspaceRepository.save(new Workspace("Test Workspace"));
    Project project = projectRepository.save(new Project(workspace, "Test Project"));
    this.projectId = project.getId();

    String uniqueUsername = "test-user-" + UUID.randomUUID();
    appUserRepository.save(new AppUser(uniqueUsername, passwordEncoder.encode("test-password")));

    var loginPayload =
        """
        { "username": "%s", "password": "test-password" }
        """
            .formatted(uniqueUsername);

    this.authToken =
        restTestClient
            .post()
            .uri("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .body(loginPayload)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(com.amuryllis.teamflow_backend.auth.LoginResponse.class)
            .returnResult()
            .getResponseBody()
            .token();
  }

  @Test
  void listingStories_withoutAuth_returns401() {
    restTestClient
        .get()
        .uri("/api/stories?projectId=" + projectId)
        .exchange()
        .expectStatus()
        .isUnauthorized();
  }

  @Test
  void creatingStory_thenUpdatingStatus_persistsCorrectly() {
    var createPayload =
        """
        {
            "projectId": %d,
            "title": "Test story",
            "description": "Created during integration test"
        }
        """
            .formatted(projectId);

    var createResponse =
        restTestClient
            .post()
            .uri("/api/stories")
            .header("Authorization", "Bearer " + authToken)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createPayload)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(StoryResponse.class)
            .returnResult()
            .getResponseBody();

    assertThat(createResponse).isNotNull();
    assertThat(createResponse.status()).isEqualTo(StoryStatus.TODO);

    var updatePayload =
        """
        { "status": "IN_PROGRESS" }
        """;

    restTestClient
        .patch()
        .uri("/api/stories/" + createResponse.id() + "/status")
        .header("Authorization", "Bearer " + authToken)
        .contentType(MediaType.APPLICATION_JSON)
        .body(updatePayload)
        .exchange()
        .expectStatus()
        .isOk();

    Story updated = storyRepository.findById(createResponse.id()).orElseThrow();
    assertThat(updated.getStatus()).isEqualTo(StoryStatus.IN_PROGRESS);
  }
}
