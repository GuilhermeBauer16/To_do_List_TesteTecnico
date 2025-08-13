package com.ToDoListTesteTecnico.repository;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<TaskEntity, String>, JpaSpecificationExecutor<TaskEntity> {

    @Query("SELECT t FROM TaskEntity t WHERE t.status = :status")
    Page<TaskEntity> findAllTasksByStatus(@Param("status") Status  status, Pageable pageable);

    @Query("SELECT t FROM TaskEntity t WHERE t.priority = :priority")
    Page<TaskEntity> findAllTasksByPriority(@Param("priority") Priority priority, Pageable pageable);

    @Query("SELECT t FROM TaskEntity t WHERE t.dueDate = :dueDate")
    Page<TaskEntity> findAllTasksByDueDate(@Param("dueDate") LocalDateTime dueDate, Pageable pageable);
}
