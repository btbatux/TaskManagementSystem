package com.btbatux.taskmanagement.controller;

import com.btbatux.taskmanagement.dto.TaskResponseDto;
import com.btbatux.taskmanagement.dto.TaskUpdateDto;
import com.btbatux.taskmanagement.model.Task;
import com.btbatux.taskmanagement.model.User;
import com.btbatux.taskmanagement.service.EmailService;
import com.btbatux.taskmanagement.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(TaskController.class);


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    private EmailService emailService;


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


    /**
     * Yeni bir görev oluşturur.
     *
     * @param task - oluşturulacak görev
     * @return ResponseEntity<TaskDTO> - oluşturulan görev
     */
    @PostMapping("/createTask")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody Task task) {
        //Görev create edilirse loglansın.
        logger.info("Görev oluşturulmaya başlandı Controller: " + task.getUser().getId() + " " + task.getUser());
        TaskResponseDto createdTask = taskService.saveTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<UUID> deleteTaskById(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @DeleteMapping("/delete-by-title/{title}")
    public ResponseEntity<String> deleteTaskByTitle(@PathVariable String title) {
        taskService.deleteByTitle(title);
        return new ResponseEntity<>(title + " Başlıklı Görev Silindi.", HttpStatus.OK);
    }


    /**
     * Görev günceller.
     *
     * @param taskUpdateDto - oluşturulacak görev
     * @return ResponseEntity<TaskResponseDto> - oluşturulan görev
     */
    @PutMapping("/update-task")
    public ResponseEntity<TaskResponseDto> updateTask(@RequestBody TaskUpdateDto taskUpdateDto) {
        TaskResponseDto taskResponseDto = taskService.taskUpdate(taskUpdateDto);
        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
    }


    //Tüm Görevlerin en eskiden en yeniye sıralaması
    @GetMapping("/old-by-start-date")
    public ResponseEntity<List<TaskResponseDto>> getAllTasksOrderByStartDateOld() {
        List<TaskResponseDto> allTaskOrderByStartDateOldList = taskService.getAllTaskOrderByStartDateOld();
        return new ResponseEntity<>(allTaskOrderByStartDateOldList, HttpStatus.OK);

    }

    //Tüm Görevlerin en yeniden en eskiye sıralaması
    @GetMapping("/new-by-start-date")
    public ResponseEntity<List<TaskResponseDto>> getAllTasksOrderByStartDateNew() {
        List<TaskResponseDto> allTaskOrderByStartDateNewList = taskService.getAllTaskOrderByStartDateNew();
        return new ResponseEntity<>(allTaskOrderByStartDateNewList, HttpStatus.OK);
    }

    //Tüm Userların tamamLADIĞI görevler
    @GetMapping("/completed-task")
    public ResponseEntity<List<TaskResponseDto>> getCompletedTasks() {
        List<TaskResponseDto> allCompletedTaskList = taskService.getAllCompletedTasks();
        return new ResponseEntity<>(allCompletedTaskList, HttpStatus.OK);
    }

    //Tüm Userların tamamLAMADIĞI görevler
    @GetMapping("/not-completed")
    public ResponseEntity<List<TaskResponseDto>> getNotCompletedTasks() {
        List<TaskResponseDto> allNotCompletedTaskList = taskService.getAllNotCompletedTasks();
        return new ResponseEntity<>(allNotCompletedTaskList, HttpStatus.OK);
    }

    //Tüm Userların toplam görev sayısı
    @GetMapping("/count-all")
    public Long getAllTasksCount() {
        return taskService.countAllTask();
    }

    //Tüm Userların tamamladığı görev sayısı
    @GetMapping("/count-completed")
    public Long getCompletedTasksCount() {
        return taskService.countAllCompletedTasks();
    }


    //User'in tamamladığı görevler
    @GetMapping("/completed-tasks/{userId}")
    public ResponseEntity<List<TaskResponseDto>> getCompletedTasks(@PathVariable Long userId) {
        List<TaskResponseDto> taskResponseDtoList = taskService.findByUserIdAndCompletedTrue(userId);
        return new ResponseEntity<>(taskResponseDtoList, HttpStatus.OK);

    }

    @GetMapping("/endDateTask")
    public Stream<User> sendTaskCompletionEmails() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Task> tasks = taskService.findByEndDate(tomorrow);


       return  tasks.stream().map(task -> task.getUser());


    }
}

