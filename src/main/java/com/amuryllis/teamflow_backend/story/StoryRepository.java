package com.amuryllis.teamflow_backend.story;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
  List<Story> findByProjectId(Long projectId);
}
