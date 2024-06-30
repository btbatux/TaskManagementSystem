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
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        TaskResponseDto taskResponseDto = taskService.getTaskById(id);
        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
    }


    /**
     * Yeni bir görev oluşturur.
     *
     * @param taskRequestDto - oluşturulacak görev
     * @return ResponseEntity<TaskDTO> - oluşturulan görev
     */
    @PostMapping("/createTask")
    public ResponseEntity<TaskRequestDto> createTask(@RequestBody TaskRequestDto taskRequestDto) {
        TaskRequestDto createdTask = taskService.saveTask(taskRequestDto);
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

    @GetMapping("/count-completed-task")
    public Long getCompletedTasksCount() {
        return taskService.countAllCompletedTasks();
    }

}

