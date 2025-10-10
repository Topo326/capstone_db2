package com.controller;

import com.model.Category;
import com.model.Task;
import com.DAO.TaskDAO;
import com.controller.util.SessionData;
import com.model.enums.TaskState;

import java.time.LocalDateTime;
import java.util.List;


public class TaskController {

    private final TaskDAO dao = new TaskDAO();

    public List<Task> getMyTasks(TaskState filter) {
        int uid = SessionData.getLoggedUser().getId();
        if (filter == null) {
            return dao.listByUser(uid);
        }
        return dao.listByUserAndCurrentState(uid, filter);
    }

    public void agregarTarea(String nombre, String descripcion, TaskState estado, Category categoria, LocalDateTime endDate) {
        int userId = SessionData.getLoggedUser().getId();
        dao.addTask(nombre, descripcion, estado, categoria, userId, endDate);
    }
}
