package org.simple;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
public class ToDoController {

    public static final String FILE_NAME = "filename.txt";

    private List<String> todoList = null;

    private List<String> getTodoList() throws IOException {
        if (todoList == null) {
            todoList = FileUtils.readList(FILE_NAME);
        }

        return todoList;
    }

    private void saveAndRedirect(HttpServletResponse httpResponse) throws IOException {
        FileUtils.writeList(FILE_NAME, getTodoList());
        httpResponse.sendRedirect("/");
    }

    @RequestMapping("/api/list")
    public List<String> index() throws IOException {
        return getTodoList();
    }

    @RequestMapping("/api/add")
    public void add(@RequestParam String newToDo, HttpServletResponse httpResponse) throws IOException {
        getTodoList().add(newToDo);
        saveAndRedirect(httpResponse);
    }

    @RequestMapping("/api/delete" )
    public void delete(@RequestParam String id, HttpServletResponse httpResponse)  throws IOException {
        getTodoList().remove(Integer.parseInt(id));
        saveAndRedirect(httpResponse);
    }

    @RequestMapping("/api/edit")
    public void edit(@RequestParam String newTitle, @RequestParam String id, HttpServletResponse httpResponse) throws IOException {
        getTodoList().set(Integer.parseInt(id), newTitle);
        saveAndRedirect(httpResponse);
    }
}




