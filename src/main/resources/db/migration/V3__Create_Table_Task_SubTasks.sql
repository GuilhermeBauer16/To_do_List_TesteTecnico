CREATE TABLE task_subtasks
(
    task_id    CHAR(36) NOT NULL,
    subtask_id CHAR(36) NOT NULL,
    PRIMARY KEY (task_id, subtask_id),
    FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
    FOREIGN KEY (subtask_id) REFERENCES subtasks (id) ON DELETE CASCADE

);
