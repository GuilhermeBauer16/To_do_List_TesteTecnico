package com.ToDoListTesteTecnico.repository;

import com.ToDoListTesteTecnico.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {
}
