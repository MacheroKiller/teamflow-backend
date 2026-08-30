CREATE TABLE stories (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    assignee_id BIGINT REFERENCES app_users(id) ON DELETE SET NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'TODO',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT chk_story_status CHECK (status IN ('TODO', 'IN_PROGRESS', 'DONE'))
);

CREATE INDEX idx_stories_project_id ON stories (project_id);
CREATE INDEX idx_stories_assignee_id ON stories (assignee_id);
