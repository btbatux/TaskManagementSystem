package com.btbatux.taskmanagement.dto;

import com.btbatux.taskmanagement.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


public class TaskUpdateDto {
    private UUID id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean completed;
//    private Long userId;
//
//
//    public Long getUser() {
//        return userId;
//    }
//
//    public void setUser(Long userId) {
//        this.userId = userId;
//    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
