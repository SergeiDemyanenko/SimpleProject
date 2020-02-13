package org.simple;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.List;

@RestController
public class ToDoController {

    @RequestMapping("/api/list")
    public List<String> index() throws SQLException {
        return DataBaseUtils.getTodoList();
    }

    @RequestMapping("/api/add")
    public void add(@RequestParam String newToDo, HttpServletResponse httpResponse) throws SQLException {
        DataBaseUtils.getTodoList().add(newToDo);
    }

    @RequestMapping("/api/delete")
    public void delete(@RequestParam int id, HttpServletResponse httpResponse) throws SQLException {
        DataBaseUtils.deleteRecord(id);
    }

    @RequestMapping("/api/edit")
    public void edit(@RequestParam String newTitle, @RequestParam int id, HttpServletResponse httpResponse) throws SQLException {
        DataBaseUtils.editRecord(id, newTitle);
    }

    @RequestMapping("/api/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/api/list-it")
    public List<ToDoItem> indexIT() throws SQLException {
        return DataBaseUtils.getTodoListIT();
    }
}