package com.btbatux.taskmanagement.controller;

import com.btbatux.taskmanagement.dto.TaskRequestDto;
import com.btbatux.taskmanagement.dto.TaskResponseDto;
import com.btbatux.taskmanagement.dto.TaskUpdateDto;
import com.btbatux.taskmanagement.model.Task;
import com.btbatux.taskmanagement.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    /**
     * Tüm görevleri getirir.
     *
     * @return ResponseEntity<List < Task>> - tüm görevlerin listesi
     */
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getTasks() {
        List<TaskResponseDto> taskDto = taskService.getAllTasks();
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }


    /**
     * ID'ye göre bir görevi getirir.
     *
     * @param id - görev ID'si
     * @return ResponseEntity<Task> - bulunan görev
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable UUID id) {
        TaskResponseDto taskResponseDto = taskService.getTaskById(id);
        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
    }

    @DeleteMapping  ("/delete-by-id/{id}")
    public ResponseEntity<UUID> deleteTaskById(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @DeleteMapping("/title/{title}")
    public ResponseEntity<String> deleteTaskByTitle(@PathVariable String title) {
        taskService.deleteByTitle(title);
        return new ResponseEntity<>("Silindi.",HttpStatus.OK);
    }


    /**
     * Yeni bir görev oluşturur.
     *
     * @param taskRequestDto - oluşturulacak görev
     * @return ResponseEntity<TaskDTO> - oluşturulan görev
     */
    @PostMapping("/createTask")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto createdTask = taskService.saveTask(taskRequestDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    /**
     * Yeni bir görev oluşturur.
     *
     * @param taskUpdateDto - oluşturulacak görev
     * @return ResponseEntity<TaskResponseDto> - oluşturulan görev
     */
    @PutMapping("/update-task")
    public ResponseEntity<TaskResponseDto> updateTask(@RequestBody TaskUpdateDto taskUpdateDto) {
        TaskResponseDto taskResponseDto = taskService.taskUpdate(taskUpdateDto);
        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
    }


    @GetMapping("/old-by-start-date")
    public ResponseEntity<List<TaskResponseDto>> getAllTasksOrderByStartDateOld() {
        List<TaskResponseDto> allTaskOrderByStartDateOldList = taskService.getAllTaskOrderByStartDateOld();
        return new ResponseEntity<>(allTaskOrderByStartDateOldList, HttpStatus.OK);

    }

    @GetMapping("/new-by-start-date")
    public ResponseEntity<List<TaskResponseDto>> getAllTasksOrderByStartDateNew() {
        List<TaskResponseDto> allTaskOrderByStartDateNewList = taskService.getAllTaskOrderByStartDateNew();
        return new ResponseEntity<>(allTaskOrderByStartDateNewList, HttpStatus.OK);
    }

    @GetMapping("/completed-task")
    public ResponseEntity<List<TaskResponseDto>> getCompletedTasks() {
        List<TaskResponseDto> allCompletedTaskList = taskService.getAllCompletedTasks();
        return new ResponseEntity<>(allCompletedTaskList, HttpStatus.OK);
    }

    @GetMapping("/not-completed")
    public ResponseEntity<List<TaskResponseDto>> getNotCompletedTasks() {
       List<TaskResponseDto> allNotCompletedTaskList =  taskService.getAllNotCompletedTasks();
       return new ResponseEntity<>(allNotCompletedTaskList, HttpStatus.OK);
    }

    @GetMapping("/count-all")
    public Long getAllTasksCount() {
        return taskService.countAllTask();
    }

    @GetMapping("/count-completed")
    public Long getCompletedTasksCount() {
        return taskService.countAllCompletedTasks();
    }

}

