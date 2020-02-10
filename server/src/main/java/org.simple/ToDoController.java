package org.simple;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ToDoController {

    public static final String FILE_NAME = "filename.txt";

    private List<String> todoList = null;

    private List<String> getTodoList() throws IOException, SQLException {
        if (todoList == null) {
            todoList = new ArrayList<>();

            Connection conn = DataBase.getConnect();
            Statement sql_stmt = conn.createStatement();
            ResultSet rset = sql_stmt.executeQuery("SELECT id, text FROM todo_list");
            while (rset.next()) {
                todoList.add(rset.getString("text"));
            }
        }

        return todoList;
    }

    private void saveAndRedirect(HttpServletResponse httpResponse) throws IOException, SQLException {
        FileUtils.writeList(FILE_NAME, getTodoList());
        httpResponse.sendRedirect("/");
    }

    @RequestMapping("/api/list")
    public List<String> index() throws IOException, SQLException {
        return getTodoList();
    }

    @RequestMapping("/api/add")
    public void add(@RequestParam String newToDo, HttpServletResponse httpResponse) throws IOException, SQLException {
        getTodoList().add(newToDo);
        saveAndRedirect(httpResponse);
    }

    @RequestMapping("/api/delete" )
    public void delete(@RequestParam String id, HttpServletResponse httpResponse) throws IOException, SQLException {
        getTodoList().remove(Integer.parseInt(id));
        saveAndRedirect(httpResponse);
    }

    @RequestMapping("/api/edit")
    public void edit(@RequestParam String newTitle, @RequestParam String id, HttpServletResponse httpResponse) throws IOException, SQLException {
        getTodoList().set(Integer.parseInt(id), newTitle);
        saveAndRedirect(httpResponse);
    }

    @RequestMapping("/api/hello")
    public String hello() {
            return "hello";
    }
}




