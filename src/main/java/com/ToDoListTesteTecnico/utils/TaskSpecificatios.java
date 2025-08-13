package com.ToDoListTesteTecnico.utils;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TaskSpecificatios {

    public static Specification<TaskEntity> withFilters(Status status, Priority priority, LocalDateTime dueDate) {
        return safeWhere(statusEquals(status))
                .and(priorityEquals(priority))
                .and(dueDateEquals(dueDate));
    }

    private static <T> Specification<T> safeWhere(Specification<T> spec) {
        return spec == null ? (root, query, cb) -> cb.conjunction() : spec;
    }

    private static Specification<TaskEntity> statusEquals(Status status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    private static Specification<TaskEntity> priorityEquals(Priority priority) {
        return (root, query, cb) ->
                priority == null ? null : cb.equal(root.get("priority"), priority);
    }

    private static Specification<TaskEntity> dueDateEquals(LocalDateTime dueDate) {
        return (root, query, cb) ->
                dueDate == null ? null : cb.equal(root.get("dueDate"), dueDate);
    }


}
