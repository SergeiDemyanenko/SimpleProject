package org.simple;

import org.simple.entity.ToDoItem;
import org.simple.util.ToDoGroupUtils;
import org.simple.util.ToDoItemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ToDoController {

    @Autowired
    private ToDoItemUtils todoItem;

    @Autowired
    private ToDoGroupUtils toDoGroupUtils;

    @RequestMapping("/api/list-obj")
    public List<ToDoItem> listObj() {
        return this.todoItem.getToDoList();
    }

    @RequestMapping("/api/list")
    public List<String> list() throws SQLException {
        return DataBaseUtils.getTodoList();
    }

    @RequestMapping("/api/list-group")
    public List<ToDoFormedGroup> listGroup(){
        return DataBaseUtils.getFormedGroup();
    }

    @RequestMapping("/api/add")
    public int add(@RequestParam String text) throws SQLException {
        return DataBaseUtils.addRecord(text);
    }

    @RequestMapping("/api/delete")
    public void delete(@RequestParam int id) throws SQLException {
        DataBaseUtils.deleteRecord(id);
    }

    @RequestMapping("/api/edit")
    public void edit(@RequestParam String text, @RequestParam int id) throws SQLException {
        DataBaseUtils.editRecord(id, text);
    }

    @RequestMapping("/api/hello")
    public String hello() {
            return "hello";
    }
}