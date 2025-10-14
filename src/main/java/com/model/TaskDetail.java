package com.model;

import com.model.enums.TaskState;
import com.model.enums.TaskStateConverter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TaskDetail")
public class TaskDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskDetail_id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Task_id", nullable = false)
    private Task task;
    @Column(name = "TaskDetail_description")
    private String description;
    @Column(name = "TaskDetail_Init_Date")
    private LocalDateTime initDate;
    @Column(name = "TaskDetail_End_Date")
    private LocalDateTime endDate;
    @Convert(converter = TaskStateConverter.class)
    @Column(name = "TaskDetail_State", length = 20)
    private TaskState state;
    public TaskDetail() {}
    public TaskDetail(Task task, String description, LocalDateTime initDate, LocalDateTime endDate, TaskState state) {
        this.task = task;
        this.description = description;
        this.initDate = initDate;
        this.endDate = endDate;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }
    public String toString(){
        return "description: " + getDescription() + ", task: " + getTask() + ", state: " + getState() + ", initDate: " + getInitDate() + ", endDate: " + getEndDate();
    }
}
