ALTER TABLE subtasks
    ADD COLUMN user_id CHAR(36) NOT NULL,
    ADD CONSTRAINT fk_subtask_user
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE CASCADE;
