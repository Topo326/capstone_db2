package com.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Team_id")
    private int id;

    @Column(name = "Team_name", nullable = false, length = 100)
    private String name;

    @Column(name = "Team_description")
    private String description;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskUser> teamAssignments = new HashSet<>();

    public Team(){}
    public Team(int id, String name, String description, Set<TaskUser> teamAssignments) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.teamAssignments = teamAssignments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TaskUser> getTeamAssignments() {
        return teamAssignments;
    }

    public void setTeamAssignments(Set<TaskUser> teamAssignments) {
        this.teamAssignments = teamAssignments;
    }

    public String toString(){
        return "name: " + getName() + ", description: " + getDescription();
    }
}
