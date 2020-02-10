package org.simple;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

@RestController
public class ToDoController {

    public ToDoController() throws SQLException {
    }

    List<String> todoList = DataBaseHelper.getTodoList();

    private void redirect(HttpServletResponse httpResponse) throws IOException, SQLException {
        httpResponse.sendRedirect("/");
    }

    @RequestMapping("/api/list")
    public List<String> index() throws IOException, SQLException {
        return DataBaseHelper.getTodoList();
    }

    @RequestMapping("/api/add")
    public void add(@RequestParam String newToDo, HttpServletResponse httpResponse) throws IOException, SQLException {
        DataBaseHelper.addNewNote(newToDo);
        redirect(httpResponse);
    }

    @RequestMapping("/api/delete" )
    public void delete(@RequestParam String id, HttpServletResponse httpResponse) throws IOException, SQLException {
        DataBaseHelper.getTodoList().remove(Integer.parseInt(id));
        redirect(httpResponse);
    }

    @RequestMapping("/api/edit")
    public void edit(@RequestParam String newTitle, @RequestParam String id, HttpServletResponse httpResponse) throws IOException, SQLException {
        DataBaseHelper.getTodoList().set(Integer.parseInt(id), newTitle);
        redirect(httpResponse);
    }
}