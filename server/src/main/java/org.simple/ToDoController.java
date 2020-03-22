package org.simple;

import org.simple.entity.ToDoGroup.ToDoGroup;
import org.simple.entity.ToDoGroup.ToDoGroupRepository;
import org.simple.entity.ToDoItem.ToDoItem;
import org.simple.entity.ToDoItem.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToDoController {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @Autowired
    private ToDoGroupRepository toDoGroupRepository;

    @RequestMapping("/api/list-obj")
    public List<ToDoItem> listObj() {
        return toDoItemRepository.findAll();
    }

    @RequestMapping("/api/list")
    public List<String> list() {
        return toDoItemRepository.getToDoTexts();
    }

    @RequestMapping("/api/list-group")
    public List<ToDoGroup> listGroup(){
        return toDoGroupRepository.findAllByOrderById();
    }

    @RequestMapping(value = "/api/add", method = RequestMethod.POST)
    public ToDoItem add(@RequestBody ToDoItem toDoItem) {
        return toDoItemRepository.save(toDoItem);
    }

    @RequestMapping(value = "/api/delete", method = RequestMethod.DELETE)
    public void delete(@RequestParam Integer id) {
        toDoItemRepository.deleteById(id);
    }

    @RequestMapping(value = "/api/edit", method = RequestMethod.PATCH)
    public void edit(@RequestBody ToDoItem toDoItem){
        toDoItemRepository.save(toDoItem);
    }

    @RequestMapping("/api/hello")
    public String hello() {
            return "hello";
    }
}