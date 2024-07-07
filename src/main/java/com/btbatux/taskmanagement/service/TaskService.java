package com.btbatux.taskmanagement.service;
import com.btbatux.taskmanagement.dto.TaskResponseDto;
import com.btbatux.taskmanagement.dto.TaskUpdateDto;
import com.btbatux.taskmanagement.exception.ResourceNotFoundException;
import com.btbatux.taskmanagement.model.Task;
import com.btbatux.taskmanagement.model.User;
import com.btbatux.taskmanagement.repository.TaskRepository;
import com.btbatux.taskmanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

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
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Görev Bulunamadı. " + id));
        return modelMapper.map(task, TaskResponseDto.class);
    }

    /**
     * Yeni bir görev kaydeder veya mevcut bir görevi günceller.
     *
     * @param task - kaydedilecek veya güncellenecek görev DTO'su
     * @return TaskResponseDto - kaydedilen veya güncellenen görevin DTO'su
     */
    public TaskResponseDto saveTask(Task task) {
        logger.info("Task Oluşturma  Servisi "+ task.getUser().getId() + " " + task.getTitle());
        User user = userRepository.findById(task.getUser().getId()).orElseThrow(() ->
                new ResourceNotFoundException("User not found. " + task.getUser().getId()));
        task.setUser(user);
        logger.info("Task oluşturma başarılı "+ task.getUser().getId() + " " + task.getUser().getUsername());
        return modelMapper.map(taskRepository.save(task), TaskResponseDto.class);

    }



    public TaskResponseDto taskUpdate(TaskUpdateDto taskUpdateDto) {
        Optional<Task> optionalTask = taskRepository.findById(taskUpdateDto.getId());
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            User existingUser = existingTask.getUser(); // Mevcut kullanıcıyı saklayın

            // Güncellenen alanları mevcut task objesine kopyalayın
            modelMapper.map(taskUpdateDto, existingTask);

            // Kullanıcıyı ayarla
            existingTask.setUser(existingUser);

            Task updatedTask = taskRepository.save(existingTask);
            return modelMapper.map(updatedTask, TaskResponseDto.class);
        }
        throw new ResourceNotFoundException("Task not found");
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

    //Eskiden yeniye tüm userlar için
    public List<TaskResponseDto> getAllTaskOrderByStartDateOld() {
        List<Task> taskList = taskRepository.findAllByOrderByStartDateAsc();
        if (taskList.isEmpty()) {
            throw new ResourceNotFoundException("Eskiden yeniye Görev bulunamadı");
        }

        return taskList.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }

    //Yeniden eskiye tüm userlar için
    public List<TaskResponseDto> getAllTaskOrderByStartDateNew() {
        List<Task> taskList = taskRepository.findAllByOrderByStartDateDesc();
        if (taskList.isEmpty()) {
            throw new ResourceNotFoundException("Yeniden Eskiye Görev bulunamadı");
        }
        return taskList.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }

    //Tamamlanmış görevler tüm userlar için
    public List<TaskResponseDto> getAllCompletedTasks() {
        List<Task> completedTasks = taskRepository.findByCompletedIsTrue();
        if (completedTasks.isEmpty()) {
            throw new ResourceNotFoundException("Tamamlanmış görev bulunamadı");
        }
        return completedTasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }

    //Tamamlanmamış görevler tüm userlar için
    public List<TaskResponseDto> getAllNotCompletedTasks() {
        List<Task> notCompletedTasks = taskRepository.findByCompletedIsFalse();
        if (notCompletedTasks.isEmpty()) {
            throw new ResourceNotFoundException("Tamamlanmamış görev bulunamadı");
        }
        return notCompletedTasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }

    //Toplam görev sayısı tüm userlar için
    public Long countAllTask() {
        return taskRepository.countAllBy();
    }

    //Tamamlanmış toplam görev sayısı tüm userlar için
    public Long countAllCompletedTasks() {
        Long count = taskRepository.countByCompletedIsTrue();
        if (count == 0) {
            throw new ResourceNotFoundException("Tamamlanmış Görev Yok.");
        }
        return count;
    }


    //Userin tamamladığı görevler
    public List<TaskResponseDto> findByUserIdAndCompletedTrue(Long userId) {
        List<Task> completedTasks = taskRepository.findByUserIdAndCompletedTrue(userId);
        if (completedTasks.isEmpty()) {
            throw new ResourceNotFoundException("Tamamlanmış Görev Yok.");
        }
        return completedTasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }


}
