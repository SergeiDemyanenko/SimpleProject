package org.simple;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.List;

@RestController
public class ToDoController {

    @RequestMapping("/api/list")
    public List<String> index() throws IOException, SQLException {
        return DataBaseUtils.getTodoList();
    }

    @RequestMapping("/api/add")
    public void add(@RequestParam String newToDo, HttpServletResponse httpResponse) throws IOException, SQLException {
        DataBaseUtils.addNewNote(newToDo);
        //saveAndRedirect(httpResponse);
    }

    @RequestMapping("/api/delete")
    public void delete(@RequestParam String id, HttpServletResponse httpResponse) throws IOException, SQLException {
        DataBaseUtils.deleteRecord(id);
        DataBaseUtils.getTodoList();
    }

    @RequestMapping("/api/edit")
    public void edit(@RequestParam String newTitle, @RequestParam String id, HttpServletResponse httpResponse) throws IOException, SQLException {
        DataBaseUtils.getTodoList().set(Integer.parseInt(id), newTitle);
    }

    @RequestMapping("/api/hello")
    public String hello() {
            return "hello";
    }
}




