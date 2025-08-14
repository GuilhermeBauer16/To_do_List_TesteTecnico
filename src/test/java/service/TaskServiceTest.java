package service;

import com.ToDoListTesteTecnico.Enum.Priority;
import com.ToDoListTesteTecnico.Enum.Status;
import com.ToDoListTesteTecnico.Enum.UserProfile;
import com.ToDoListTesteTecnico.entity.SubtaskEntity;
import com.ToDoListTesteTecnico.entity.TaskEntity;
import com.ToDoListTesteTecnico.entity.UserEntity;
import com.ToDoListTesteTecnico.entity.values.TaskVO;
import com.ToDoListTesteTecnico.entity.values.UserVO;
import com.ToDoListTesteTecnico.exception.subtask.SubTaskNotCompletedException;
import com.ToDoListTesteTecnico.exception.task.InvalidTaskStatusException;
import com.ToDoListTesteTecnico.exception.task.TaskNotFoundException;
import com.ToDoListTesteTecnico.repository.TaskRepository;
import com.ToDoListTesteTecnico.request.UpdateStatusRequest;
import com.ToDoListTesteTecnico.service.TaskService;
import com.ToDoListTesteTecnico.service.user.UserService;
import com.ToDoListTesteTecnico.utils.ValidatorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    private static final String TASK_NOT_FOUND_EXCEPTION_MESSAGE = "This Task was not found";
    private static final String INVALID_TASK_EXCEPTION_MESSAGE = "This Task is invalid, please verify the field and try again";
    private static final String SUBTASK_NOT_COMPLETED_EXCEPTION_MESSAGE = "Is not possible updated a task with subtasks not completed yet";
    private static final String INVALID_TASK_STATUS_EXCEPTION_MESSAGE = "Is not possible create a task with completed status";

    private static final String ID = "cae1ee94-fd8c-4aa3-bd1b-c7a20433afdb";
    private static final String TITLE = "Finish Spring Boot API";
    private static final String DESCRIPTION = "Spring Boot API";
    private static final LocalDateTime DUE_DATE = LocalDateTime.now();
    private static final Status STATUS = Status.IN_PROGRESS;
    private static final Priority PRIORITY = Priority.HIGH;
    private List<SubtaskEntity> subTasks = new ArrayList<>();

    private static final String USER_NAME = "John Doe";
    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "12345678";
    private static final UserProfile USER_PROFILE = UserProfile.ROLE_USER;

    private UpdateStatusRequest updateStatusRequest;
    private SubtaskEntity subtaskEntity;
    private UserEntity userEntity;
    private UserVO userVO;
    private TaskVO taskVO;

    private TaskEntity taskEntity;
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {

        updateStatusRequest = new UpdateStatusRequest(STATUS);
        userEntity = new UserEntity(ID, USER_NAME, EMAIL, PASSWORD, USER_PROFILE);
        userVO = new UserVO(ID, USER_NAME, EMAIL, PASSWORD, USER_PROFILE);

        subtaskEntity = new SubtaskEntity(ID, TITLE, DESCRIPTION, DUE_DATE, STATUS, PRIORITY, userEntity);
        taskVO = new TaskVO(ID, TITLE, DESCRIPTION, DUE_DATE, STATUS, PRIORITY,

                new ArrayList<SubtaskEntity>(Arrays.asList(new SubtaskEntity())), userEntity);

        taskEntity = new TaskEntity(ID, TITLE, DESCRIPTION, DUE_DATE, STATUS, PRIORITY,
                new ArrayList<SubtaskEntity>(Arrays.asList(subtaskEntity)), new UserEntity());


        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(EMAIL);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCreateTask_WhenSuccessful_ShouldReturnTask() {

        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = mockStatic(ValidatorUtils.class)) {

            mockedValidatorUtils.when(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(),
                    anyString(), any())).thenAnswer(invocation -> null);
            mockedValidatorUtils.when(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(
                    any(), anyString(), any())).thenAnswer(invocation -> null);

            when(userService.findUserByEmail(EMAIL)).thenReturn(userVO);
            when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

            TaskVO createdTask = taskService.createTask(taskVO);

            verify(taskRepository, times(1)).save(any(TaskEntity.class));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any()));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()));

            assertNotNull(createdTask);
            assertNotNull(createdTask.getId());
            assertEquals(TITLE, createdTask.getTitle());
            assertEquals(DESCRIPTION, createdTask.getDescription());
            assertEquals(DUE_DATE, createdTask.getDueDate());
            assertEquals(STATUS, createdTask.getStatus());
            assertEquals(PRIORITY, createdTask.getPriority());

        }

    }

    @Test
    void testCreateTask_WhenTaskIsCreatedHowToCreated_ShouldThrowInvalidTaskStatusException() {

        when(userService.findUserByEmail(EMAIL)).thenReturn(userVO);
        taskVO.setStatus(Status.COMPLETED);
        InvalidTaskStatusException exception = assertThrows(InvalidTaskStatusException.class, () -> taskService.createTask(taskVO));
        assertNotNull(exception);
        assertEquals(INVALID_TASK_STATUS_EXCEPTION_MESSAGE, exception.getMessage());

    }

    @Test
    void testUpdateTask_WhenSuccessful_ShouldReturnUpdatedTask() {

        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = mockStatic(ValidatorUtils.class)) {

            Priority updatedPriority = Priority.MEDIUM;
            String updatedTitle = "The SpringBoot app";

            taskEntity.setPriority(updatedPriority);
            taskEntity.setTitle(updatedTitle);

            mockedValidatorUtils.when(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(),
                    anyString(), any())).thenAnswer(invocation -> null);
            mockedValidatorUtils.when(() -> ValidatorUtils.updateFieldIfNotNull(
                    any(), any(), anyString(), any())).thenAnswer(invocation -> taskEntity);

            when(taskRepository.findByIdAndUserEmail(ID, EMAIL)).thenReturn(Optional.of(taskEntity));
            when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

            TaskVO updateTask = taskService.updateTask(taskVO);
            verify(taskRepository, times(1)).findByIdAndUserEmail(anyString(), any());
            verify(taskRepository, times(1)).save(any(TaskEntity.class));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any()));
            mockedValidatorUtils.verify(() -> ValidatorUtils.updateFieldIfNotNull(any(), any(), anyString(), any()));

            assertNotNull(updateTask);
            assertNotNull(updateTask.getId());
            assertEquals(updatedTitle, updateTask.getTitle());
            assertEquals(DESCRIPTION, updateTask.getDescription());
            assertEquals(DUE_DATE, updateTask.getDueDate());
            assertEquals(STATUS, updateTask.getStatus());
            assertEquals(updatedPriority, updateTask.getPriority());

        }

    }


    @Test
    void testUpdate_WhenTaskIsNotFound_ShouldThrowTaskNotFoundException() {


        when(taskRepository.findByIdAndUserEmail(ID, EMAIL)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskVO));
        assertNotNull(exception);
        assertEquals(TASK_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void testUpdateTaskStatus_WhenSuccessful_ShouldReturnUpdatedTask() {

        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = mockStatic(ValidatorUtils.class)) {

            Status updatedStatus = Status.IN_PROGRESS;
            taskEntity.setStatus(Status.COMPLETED);
            taskEntity.getSubTasks().getFirst().setStatus(Status.COMPLETED);
            taskEntity.setStatus(updatedStatus);

            mockedValidatorUtils.when(() -> ValidatorUtils.updateFieldIfNotNull(
                    any(), any(), anyString(), any())).thenAnswer(invocation -> taskEntity);

            when(taskRepository.findByIdAndUserEmail(ID, EMAIL)).thenReturn(Optional.of(taskEntity));
            when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

            TaskVO updateTask = taskService.updateTaskStatus(ID, updateStatusRequest);
            verify(taskRepository, times(1)).findByIdAndUserEmail(anyString(), any());
            verify(taskRepository, times(1)).save(any(TaskEntity.class));
            mockedValidatorUtils.verify(() -> ValidatorUtils.updateFieldIfNotNull(any(), any(), anyString(), any()));

            assertNotNull(updateTask);
            assertNotNull(updateTask.getId());
            assertEquals(TITLE, updateTask.getTitle());
            assertEquals(DESCRIPTION, updateTask.getDescription());
            assertEquals(DUE_DATE, updateTask.getDueDate());
            assertEquals(updatedStatus, updateTask.getStatus());
            assertEquals(PRIORITY, updateTask.getPriority());

        }

    }

    @Test
    void testUpdateTaskStatus_WhenTaskIsNotFound_ShouldThrowTaskNotFoundException() {


        when(taskRepository.findByIdAndUserEmail(ID, EMAIL)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> taskService.updateTaskStatus(ID, updateStatusRequest));
        assertNotNull(exception);
        assertEquals(TASK_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void testUpdateTaskStatus_WhenSubTaskIsNotComplete_SubTaskNotCompletedException() {

        updateStatusRequest.setStatus(Status.COMPLETED);

        when(taskRepository.findByIdAndUserEmail(ID, EMAIL)).thenReturn(Optional.of(taskEntity));

        SubTaskNotCompletedException exception = assertThrows(SubTaskNotCompletedException.class,
                () -> taskService.updateTaskStatus(ID, updateStatusRequest));
        assertNotNull(exception);
        assertEquals(SUBTASK_NOT_COMPLETED_EXCEPTION_MESSAGE, exception.getMessage());
    }


    @Test
    void testFindTaskById_WhenTaskIsFound_ShouldReturnTask() {


        when(taskRepository.findByIdAndUserEmail(ID, EMAIL)).thenReturn(Optional.of(taskEntity));
        TaskVO taskById = taskService.findTaskById(ID);

        verify(taskRepository, times(1)).findByIdAndUserEmail(anyString(), anyString());

        assertNotNull(taskById);
        assertNotNull(taskById.getId());
        assertEquals(ID, taskById.getId());
        assertEquals(TITLE, taskById.getTitle());
        assertEquals(DESCRIPTION, taskById.getDescription());
        assertEquals(DUE_DATE, taskById.getDueDate());
        assertEquals(STATUS, taskById.getStatus());
        assertEquals(PRIORITY, taskById.getPriority());
        ;
    }


    @Test
    void testFindTaskById_WhenTaskIsNotFound_ShouldThrowTaskNotFoundException() {


        when(taskRepository.findByIdAndUserEmail(ID, EMAIL)).thenReturn(Optional.empty());


        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> taskService.findTaskById(ID));
        assertNotNull(exception);
        assertEquals(TASK_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }


    @Test
    void testDeleteTaskById_WhenTaskIsNotFound_ShouldThrowTaskNotFoundException() {


        when(taskRepository.findByIdAndUserEmail(ID, EMAIL)).thenReturn(Optional.empty());


        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(ID));
        assertNotNull(exception);
        assertEquals(TASK_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void testDeleteTaskById_WhenTaskIsFound_ShouldDeleteTask() {


        when(taskRepository.findByIdAndUserEmail(ID, EMAIL)).thenReturn(Optional.of(taskEntity));
        doNothing().when(taskRepository).delete(taskEntity);

        taskService.deleteTaskById(ID);

        verify(taskRepository, times(1)).findByIdAndUserEmail(anyString(), anyString());
        verify(taskRepository, times(1)).delete(any(TaskEntity.class));

    }
}
