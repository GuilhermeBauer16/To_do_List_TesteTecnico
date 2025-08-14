package com.ToDoListTesteTecnico.repository;

import com.ToDoListTesteTecnico.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, String>, JpaSpecificationExecutor<TaskEntity> {

    @Query("SELECT t FROM TaskEntity t WHERE t.id = :taskId AND t.user.email = :email")
    Optional<TaskEntity> findByIdAndUserEmail(@Param("taskId") String taskId, @Param("email") String email);
}
