package com.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Users_id")
    private int id;

    @Column(name = "Users_name", nullable = false, length = 100)
    private String username;

    @Column(name = "Users_password", nullable = false, length = 256)
    private String password;

    @Column(name = "Users_Create_Day", nullable = false, updatable = false)
    private LocalDate createDay;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskUser> tasksAssigned = new HashSet<>();

    public User() {}
    public User(String username, String password,  LocalDate createDay, Set<TaskUser> tasksAssigned) {
        this.username = username;
        this.password = password;
        this.createDay = createDay;
        this.tasksAssigned = tasksAssigned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreateDay() {
        return createDay;
    }

    public void setCreateDay(LocalDate createDay) {
        this.createDay = createDay;
    }

    public Set<TaskUser> getTasksAssigned() {
        return tasksAssigned;
    }

    public void setTasksAssigned(Set<TaskUser> tasksAssigned) {
        this.tasksAssigned = tasksAssigned;
    }

    @Override
    public String toString() {
        return  "User id =" + getId() +
                ", username=" + getUsername() +
                ", password=" + getPassword();
    }
}
