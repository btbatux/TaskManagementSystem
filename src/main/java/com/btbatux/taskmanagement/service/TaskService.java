package com.btbatux.taskmanagement.service;
import com.btbatux.taskmanagement.dto.TaskRequestDto;
import com.btbatux.taskmanagement.dto.TaskResponseDto;
import com.btbatux.taskmanagement.dto.TaskUpdateDto;
import com.btbatux.taskmanagement.exception.ResourceNotFoundException;
import com.btbatux.taskmanagement.model.Task;
import com.btbatux.taskmanagement.model.User;
import com.btbatux.taskmanagement.repository.TaskRepository;
import com.btbatux.taskmanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * TaskService, iş mantığını barındıran ve Task entity ile ilgili işlemleri gerçekleştiren bir servistir.
 * TaskRepository'yi kullanarak veritabanı işlemlerini gerçekleştirir.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }



    /**
     * Tüm görevleri getirir.
     *
     * @return List<Task> - tüm görevlerin listesi
     */
    public List<TaskResponseDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("Hiç görev yok.");
        }
        return tasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }


    /**
     * ID'ye göre bir görevi getirir.
     *
     * @param id - görev ID'si
     * @return Optional<Task> - bulunan görev (varsa)
     */
    public TaskResponseDto getTaskById(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Görev Bulunamadı. " + id));
        return modelMapper.map(task, TaskResponseDto.class);
    }

    /**
     * Yeni bir görev kaydeder veya mevcut bir görevi günceller.
     *
     * @param taskRequestDto - kaydedilecek veya güncellenecek görev
     * @return TaskDto - kaydedilen veya güncellenen görev
     */
    public TaskResponseDto saveTask(TaskRequestDto taskRequestDto) {
        // TaskDTO'yu Task entity'sine dönüştür
        Task task = modelMapper.map(taskRequestDto, Task.class);

        Optional<User> user = userRepository.findById(taskRequestDto.getUserId());
        if(user.isPresent())
        {
        task = taskRepository.save(task);
        }
        return modelMapper.map(task,TaskResponseDto.class);



    }


    public TaskResponseDto taskUpdate(TaskUpdateDto taskUpdateDto) {
        Task convertTask = modelMapper.map(taskUpdateDto, Task.class);

        Task updateTask = taskRepository.save(convertTask);
        return modelMapper.map(updateTask, TaskResponseDto.class);

    }


    /**
     * ID'ye göre bir görevi siler.
     *
     * @param id - silinecek görev ID'si
     */
    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Görev bulunamadı");
        }
        taskRepository.deleteById(id);
    }

    public boolean deleteByTitle(String title) {
       int deleteCount = taskRepository.deleteByTitle(title);
        return deleteCount > 0;
    }

    //Eskiden yeniye
    public List<TaskResponseDto> getAllTaskOrderByStartDateOld() {
        List<Task> taskList = taskRepository.findAllByOrderByStartDateAsc();
        if (taskList.isEmpty()) {
            throw new ResourceNotFoundException("Eskiden yeniye Görev bulunamadı");
        }

        return taskList.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }

    //Yeniden eskiye
    public List<TaskResponseDto> getAllTaskOrderByStartDateNew() {
        List<Task> taskList = taskRepository.findAllByOrderByStartDateDesc();
        if (taskList.isEmpty()) {
            throw new ResourceNotFoundException("Yeniden Eskiye Görev bulunamadı");
        }
        return taskList.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }

    //Tamamlanmış görevler
    public List<TaskResponseDto> getAllCompletedTasks() {
        List<Task> completedTasks = taskRepository.findByCompletedIsTrue();
        if (completedTasks.isEmpty()) {
            throw new ResourceNotFoundException("Tamamlanmış görev bulunamadı");
        }
        return completedTasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }

    //Tamamlanmamış görevler
    public List<TaskResponseDto> getAllNotCompletedTasks() {
        List<Task> notCompletedTasks = taskRepository.findByCompletedIsFalse();
        if (notCompletedTasks.isEmpty()) {
            throw new ResourceNotFoundException("Tamamlanmamış görev bulunamadı");
        }
        return notCompletedTasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }

    //Toplam görev sayısı
    public Long countAllTask() {
       return taskRepository.countAllBy();
    }

    public Long countAllCompletedTasks() {
        return taskRepository.countByCompletedIsTrue();
    }



}
