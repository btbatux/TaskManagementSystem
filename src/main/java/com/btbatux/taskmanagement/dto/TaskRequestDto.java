package com.btbatux.taskmanagement.dto;

import java.time.LocalDateTime;

/**
 * TaskRequestDto, Task entity'si için veri transfer nesnesidir.
 * Task verilerini katmanlar arasında güvenli ve kontrollü bir şekilde taşımak için kullanılır.
 */

public class TaskRequestDto {
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime startDate; // Yeni eklenen başlangıç tarihi alanı

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
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
