package com.ToDoListTesteTecnico.utils;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;


/**
 * Utility class for creating {@link Specification} objects to filter {@link TaskEntity} instances.
 * <p>
 * Provides methods to build dynamic JPA Specifications based on task status, priority, due date,
 * and the currently authenticated user's email. These specifications can be used in repository queries
 * to retrieve filtered and secure task data.
 * </p>

 */
public class TaskSpecificatios {


    /**
     * Builds a {@link Specification} for filtering {@link TaskEntity} based on the provided criteria.
     *
     * @param status   the {@link Status} of the task (optional, can be null).
     * @param priority the {@link Priority} of the task (optional, can be null).
     * @param dueDate  the due date of the task (optional, can be null).
     * @return a {@link Specification} that combines all non-null filters and also restricts results
     *         to tasks belonging to the currently authenticated user.
     *
     */

    public static Specification<TaskEntity> withFilters(Status status, Priority priority, LocalDateTime dueDate) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return safeWhere(statusEquals(status))
                .and(priorityEquals(priority))
                .and(dueDateEquals(dueDate))
                .and(userEmailEquals(email));
    }

    /**
     * Safely wraps a specification, returning a no-op specification if the input is null.
     *
     * @param spec the {@link Specification} to wrap.
     * @param <T>  the entity type.
     * @return the original specification if not null, otherwise a specification that always evaluates to true.
     *
     */

    private static <T> Specification<T> safeWhere(Specification<T> spec) {
        return spec == null ? (root, query, cb) -> cb.conjunction() : spec;
    }


    /**
     * Creates a specification to filter by task status.
     *
     * @param status the {@link Status} to filter by.
     * @return a {@link Specification} for status equality, or null if status is null.
     */

    private static Specification<TaskEntity> statusEquals(Status status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }


    /**
     * Creates a specification to filter by task priority.
     *
     * @param priority the {@link Priority} to filter by.
     * @return a {@link Specification} for priority equality, or null if priority is null.
     */
    private static Specification<TaskEntity> priorityEquals(Priority priority) {
        return (root, query, cb) ->
                priority == null ? null : cb.equal(root.get("priority"), priority);
    }


    /**
     * Creates a specification to filter by task due date.
     *
     * @param dueDate the due date to filter by.
     * @return a {@link Specification} for due date equality, or null if dueDate is null.
     */
    private static Specification<TaskEntity> dueDateEquals(LocalDateTime dueDate) {
        return (root, query, cb) ->
                dueDate == null ? null : cb.equal(root.get("dueDate"), dueDate);
    }


    /**
     * Creates a specification to filter tasks by the user's email.
     *
     * @param email the email of the currently authenticated user.
     * @return a {@link Specification} for user email equality, or null if email is null.
     */
    private static Specification<TaskEntity> userEmailEquals(String email) {
        return (root, query, cb) ->
                email == null ? null : cb.equal(root.get("user").get("email"), email);
    }


}
