package com.ToDoListTesteTecnico.utils;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class TaskSpecificatios {

    public static Specification<TaskEntity> withFilters(Status status, Priority priority, LocalDateTime dueDate) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return safeWhere(statusEquals(status))
                .and(priorityEquals(priority))
                .and(dueDateEquals(dueDate))
                .and(userEmailEquals(email));
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

    private static Specification<TaskEntity> userEmailEquals(String email) {
        return (root, query, cb) ->
                email == null ? null : cb.equal(root.get("user").get("email"), email);
    }


}
