package com.ToDoListTesteTecnico.repository;

import com.ToDoListTesteTecnico.entity.SubtaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtaskRepository extends JpaRepository<SubtaskEntity, String> {


}
