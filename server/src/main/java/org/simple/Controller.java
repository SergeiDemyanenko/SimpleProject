package org.simple;

import org.simple.entity.ToDoGroup.ToDoGroup;
import org.simple.entity.ToDoGroup.ToDoGroupRepository;
import org.simple.entity.ToDoItem.ToDoItem;
import org.simple.entity.ToDoItem.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Controller.API_PREFIX)
public class Controller {

    public static final String API_PREFIX = "/api";

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @Autowired
    private ToDoGroupRepository toDoGroupRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/list-obj")
    public List<ToDoItem> listObj() {
        return toDoItemRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public List<String> list() {
        return toDoItemRepository.getToDoTexts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list-group")
    public List<ToDoGroup> listGroup(){
        return toDoGroupRepository.findAllByOrderById();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ToDoItem add(@RequestBody ToDoItem toDoItem) {
        return toDoItemRepository.save(toDoItem);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public void delete(@RequestParam Long id) {
        toDoItemRepository.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/edit")
    public void edit(@RequestBody ToDoItem toDoItem){
        toDoItemRepository.save(toDoItem);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addGroup")
    public ToDoGroup addGroup(@RequestBody ToDoGroup toDoGroup){
        return toDoGroupRepository.save(toDoGroup);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/editGroup")
    public void editGroup(@RequestBody ToDoGroup toDoGroup){
        toDoGroupRepository.save(toDoGroup);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteGroup")
    public void deleteGroup(@RequestParam Long id){
        toDoGroupRepository.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String hello() {
        return "hello";
    }
}