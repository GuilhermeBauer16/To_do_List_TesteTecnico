package com.ToDoListTesteTecnico.repository;

import com.ToDoListTesteTecnico.entity.SubtaskEntity;
import com.ToDoListTesteTecnico.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubtaskRepository extends JpaRepository<SubtaskEntity, String> {

    @Query("SELECT st FROM SubtaskEntity st WHERE st.id = :subtaskId AND st.user.email = :email")
    Optional<SubtaskEntity> findByIdAndUserEmail(@Param("subtaskId") String subtaskId, @Param("email") String email);


}
