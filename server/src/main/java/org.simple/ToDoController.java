package org.simple;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.List;

@RestController
public class ToDoController {

    @RequestMapping("/api/list-obj")
    public List<ToDoItem> listObj() throws SQLException {
        return DataBaseUtils.getTodoListIT();
    }

    @RequestMapping("/api/list")
    public List<String> list() throws SQLException {
        return DataBaseUtils.getTodoList();
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