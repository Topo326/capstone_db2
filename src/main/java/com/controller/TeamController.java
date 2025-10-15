package com.controller;

import com.DAO.TeamDAO;
import com.model.Team;
import java.util.List;

public class TeamController {
    private final TeamDAO DAO = new TeamDAO();
    public List<Team> getAllTeams(){
        return DAO.gatAllTeams();
    }
    public void addTeam(String name, String description){
        Team newTeam = new Team();
        newTeam.setName(name);
        newTeam.setDescription(description);
        DAO.addTeam(newTeam);
    }
}
