package org.simple;

import org.simple.entity.ToDoGroup.ToDoGroup;
import org.simple.entity.ToDoGroup.ToDoGroupRepository;
import org.simple.entity.ToDoItem.ToDoItem;
import org.simple.entity.ToDoItem.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class Controller {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @Autowired
    private ToDoGroupRepository toDoGroupRepository;

    @RequestMapping("list-obj")
    public List<ToDoItem> listObj() {
        return toDoItemRepository.findAll();
    }

    @RequestMapping("list")
    public List<String> list() {
        return toDoItemRepository.getToDoTexts();
    }

    @RequestMapping("list-group")
    public List<ToDoGroup> listGroup(){
        return toDoGroupRepository.findAllByOrderById();
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ToDoItem add(@RequestBody ToDoItem toDoItem) {
        return toDoItemRepository.save(toDoItem);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public void delete(@RequestParam Long id) {
        toDoItemRepository.deleteById(id);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public ToDoItem edit(@RequestBody ToDoItem toDoItem){
        return toDoItemRepository.save(toDoItem);
    }

    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("login")
    public void getLogin(@RequestBody(required = false) Map<String, String> parameters,
                         HttpServletRequest request, HttpServletResponse response) {
        Object userName = request.getSession().getAttribute(AuthorizationInterceptor.USER_PARAM);
        if (userName == null) {
            request.getSession().setAttribute(AuthorizationInterceptor.USER_PARAM, "user");
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}