package com.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer id;
    @Column(name = "Task_name", nullable = false, length = 150)
    private String taskName;
    @Column(name = "Task_Create_Date", updatable = false)
    private LocalDateTime createDate;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<TaskDetail> details = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",  nullable = false)
    private Category category;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskUser> assignments = new HashSet<>();
    public Task(){}
    public Task(Integer id, String taskName, LocalDateTime createDate, Set<TaskDetail> details, Category category,  Set<TaskUser> assignments) {
        this.id = id;
        this.taskName = taskName;
        this.createDate = createDate;
        this.details = details;
        this.category = category;
        this.assignments = assignments;
    }

    public Set<TaskUser> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<TaskUser> assignments) {
        this.assignments = assignments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Set<TaskDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<TaskDetail> details) {
        this.details = details;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public String toString(){
        return "Task: " + getTaskName() + "Category: " + getCategory() + "Creation Date: " + getCreateDate() + "Details: " + getDetails() ;
    }
}
