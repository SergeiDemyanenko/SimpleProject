package org.simple;

import org.simple.entity.ToDoGroup;
import org.simple.entity.ToDoGroupRepository;
import org.simple.entity.ToDoItem;
import org.simple.entity.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToDoController {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @Autowired
    private ToDoGroupRepository toDoGroupRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/api/list-obj")
    public List<ToDoItem> listObj() {
        return toDoItemRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/list")
    public List<String> list() {
        return toDoItemRepository.getToDoTexts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/list-group")
    public List<ToDoGroup> listGroup(){
        return toDoGroupRepository.findAllByOrderById();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/add")
    public ToDoItem add(@RequestBody ToDoItem toDoItem) {
        return toDoItemRepository.save(toDoItem);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/delete")
    public void delete(@RequestParam Integer id) {
        toDoItemRepository.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/api/edit")
    public void edit(@RequestBody ToDoItem toDoItem){
        toDoItemRepository.save(toDoItem);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/addGroup")
    public ToDoGroup addGroup(@RequestBody ToDoGroup toDoGroup){
        return toDoGroupRepository.save(toDoGroup);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "api/editGroup")
    public void editGroup(@RequestBody ToDoGroup toDoGroup){
        toDoGroupRepository.save(toDoGroup);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "api/deleteGroup")
    public void deleteGroup(@RequestParam Integer id){
        toDoGroupRepository.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/hello")
    public String hello() {
            return "hello";
    }
}