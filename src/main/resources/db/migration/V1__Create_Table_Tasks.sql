CREATE TABLE tasks
(
    id          CHAR(36)                                    NOT NULL,
    title       VARCHAR(255)                                 NOT NULL,
    description TEXT,
    due_date    DATETIME                                     NOT NULL,
    status      ENUM ('PENDING', 'IN_PROGRESS', 'COMPLETED') NOT NULL,
    priority    ENUM ('LOW', 'MEDIUM', 'HIGH')               NOT NULL,
    PRIMARY KEY (id)

);
