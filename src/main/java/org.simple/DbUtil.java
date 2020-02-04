package org.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class DbUtil {

    @Autowired
    TaskDB TaskDB;

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<Task>();
        TaskDB.findAll().forEach(task -> tasks.add(task));
        return tasks;
    }

    public Task getTaskById(int id) {
        return TaskDB.findById(id).get();
    }

    public void saveOrUpdate(Task task) {
        TaskDB.save(task);
    }

    public void delete(int id) {
        TaskDB.deleteById(id);
    }

}
