package com.btbatux.taskmanagement.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskUpdateDto {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startDate; // Yeni eklenen başlangıç tarihi alanı
    private boolean completed;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
