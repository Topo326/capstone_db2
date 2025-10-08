/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.model.Task;
import com.DAO.TaskDAO;
import com.controller.util.SessionData;
import com.model.enums.TaskState;
import java.util.List;

/**
 *
 * @author Maro
 */
public class TaskController {

    private final TaskDAO dao = new TaskDAO();

    public List<com.model.Task> getMyTasks(TaskState filter) {
        int uid = com.controller.util.SessionData.getLoggedUser().getId();
        if (filter == null) {
            return dao.listByUser(uid);
        }
        return dao.listByUserAndCurrentState(uid, filter);
    }
}
