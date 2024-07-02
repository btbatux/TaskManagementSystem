package com.btbatux.taskmanagement.repository;

import com.btbatux.taskmanagement.model.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * TaskRepository, Task entity için CRUD işlemlerini sağlayan bir arayüzdür.
 * JpaRepository'den extend edilmiştir ve temel CRUD işlemleri için gerekli metotları sağlar.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task,UUID> {

    //Eskiden yeniye sırala
    List<Task> findAllByOrderByStartDateAsc();

    //Yeniden eskiye sırala
    List<Task> findAllByOrderByStartDateDesc();

    //Tamamlanmış görevleri getirir.
    // List<Task> findByCompletedIsTrue();
    @Query("SELECT t FROM Task t WHERE t.completed = true")
    List<Task> findByCompletedIsTrue();

    //Tamamlanmamış görevleri getirir.
    //List<Task> findByCompletedIsFalse();

    @Query("SELECT t FROM Task t WHERE t.completed = false")
    List<Task> findByCompletedIsFalse();

    Long countAllBy();

    Long countByCompletedIsTrue();

    @Transactional
    int deleteByTitle(String title);

    List<Task> findByUserIdAndCompletedTrue(Long userId);

    Long countByCompletedIsFalse();

}