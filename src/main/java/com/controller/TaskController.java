package com.controller;

import com.model.Task;
import com.DAO.TaskDAO;
import com.controller.util.SessionData;
import com.model.enums.TaskState;
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
}
