ALTER TABLE tasks
    ADD COLUMN user_id CHAR(36) NOT NULL,
    ADD CONSTRAINT fk_task_user
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE CASCADE;
