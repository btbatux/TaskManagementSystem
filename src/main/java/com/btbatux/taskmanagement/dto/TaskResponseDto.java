package com.btbatux.taskmanagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * TaskResponseDto, Task entity'si için veri transfer nesnesidir.
 * Task verilerini katmanlar arasında güvenli ve kontrollü bir şekilde taşımak için kullanılır.
 */

public class TaskResponseDto {

    private String title;
    private String description;
    private LocalDate startDate; // Yeni eklenen başlangıç tarihi alanı
    private LocalDate endDate;
    private boolean completed;
    private Long userId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
